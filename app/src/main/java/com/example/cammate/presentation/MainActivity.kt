package com.example.cammate

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.cammate.databinding.ActivityMainBinding
import com.example.cammate.presentation.make_room.MakeRoomHostFragment
import com.example.cammate.presentation.utils.setStatusBarTransparent
import com.example.cammate.presentation.find_room.FindRoomHostFragment
import com.example.cammate.webRTC.Models.MessageModel
import com.example.cammate.webRTC.utils.NewMessageInterface
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        installSplashScreen()
        setStatusBarTransparent()

        // 블루투스어댑터 초기화
        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter

        // 블루투스 지원되는지 확인
        checkSupportBT()

        initToolbar()
        initTabLayout()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkSupportBT() {
        if (bluetoothAdapter == null){
            showToast("Device doesn't support Bluetooth")
            finish() /// 지원하지 않으면 종료
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.title = "cammate"
    }

    private fun initTabLayout() {
        val tabTextList = listOf("방 만들기", "방 찾기")
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.mainViewPager
        val pagerAdapter = MainPagerAdapter(this@MainActivity)

        pagerAdapter.addFragment(MakeRoomHostFragment())
        pagerAdapter.addFragment(FindRoomHostFragment())
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("ViewPagerFragment", "Page ${position + 1}")
            }
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_setting -> true
            R.id.action_help -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}