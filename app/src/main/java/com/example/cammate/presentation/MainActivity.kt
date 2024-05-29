package com.example.cammate

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.viewpager2.widget.ViewPager2
import com.example.cammate.databinding.ActivityMainBinding
import com.example.cammate.presentation.make_room.MakeRoomHostFragment
import com.example.cammate.utils.setStatusBarTransparent
import com.example.cammate.presentation.find_room.FindRoomHostFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_ENABLE_BT: Int = 1

    private lateinit var binding: ActivityMainBinding

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter

    // Activity Result Launcher
    private lateinit var activityResultLauncherBluetooth: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStatusBarTransparent()
        initToolbar()
        initTabLayout()
    }

    fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.title = "cammate"
    }

    fun initTabLayout() {
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

}