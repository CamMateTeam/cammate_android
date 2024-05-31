package com.example.cammate.presentation.find_room

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cammate.R
import com.example.cammate.bluetooth.DiscoveryReceiver
import com.example.cammate.databinding.FragmentFindRoomBinding
import com.example.cammate.network.data.CammateRoomList
import com.example.cammate.network.data.config.RetrofitBuilder
import com.example.cammate.presentation.viewer.find_room.adapter.CammatesAdapter
import com.example.cammate.presentation.viewer.find_room.adapter.CammatesItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
class FindRoomFragment : Fragment() {
    private var _binding: FragmentFindRoomBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CammatesAdapter
    private val cammates = mutableListOf<CammatesItem>()
    lateinit private var cammatelist: CammateRoomList

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var discoveryReceiver: DiscoveryReceiver
    private lateinit var intentFilter: IntentFilter
    // private lateinit var myBluetoothAdapter: BluetoothListAdapter
    private var listMyDevice = mutableListOf<BluetoothDevice>()
    private var uniqueDevices = HashSet<BluetoothDevice>()
    private val TAG = "FindRoomFragment"

    private val enableBluetoothLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                showToast("Bluetooth turned on")
            } else {
                showToast("Bluetooth enabling canceled")
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindRoomBinding.inflate(inflater, container, false)

        // 블루투스어댑터 초기화
        bluetoothManager = requireContext().getSystemService(BluetoothManager::class.java)
            ?: throw IllegalStateException("BluetoothManager not found")
        bluetoothAdapter = bluetoothManager.adapter

        // 블루투스 버튼 클릭 이벤트 리스너
        binding.ivFindRoomBluetoothBtn.setOnClickListener {
            showToast("블루투스 버튼 클릭")
            enableBT()
        }

        // 불러온 맥주소 목록들
        val macAddress = "2.2.2.2" // 맥주소 더미 데이터

        // 레트로핏 방찾기 api 통신
        lifecycleScope.launch {
            try {
                val response = RetrofitBuilder.api.getRooms(macAddress)
                Log.d("FindRoomFragment", "Room Response: $response")
                for (room in response.data) {
                    cammates.add(CammatesItem(room.nickname))
                }
                Log.d("FindRoomFragment", "cammmate list : $cammates")
                initRecyclerView()
            } catch (e: Exception) {
                Log.e("FindRoomFragment", "Error: ${e.message}")
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 다음 버튼 클릭 이벤트 리스너
        binding.btnFindRoomNext.setOnClickListener {
            val selectedItem = adapter.getSelectedItem()
            if (selectedItem != null) {
                val bundle = Bundle().apply {
                    putString("selected_cammate", selectedItem.roomName)
                }
                findNavController().navigate(R.id.action_findRoomFragment_to_enterRoomFragment, bundle)
            } else {
                showToast("방을 선택하세요.")
            }
        }

       /* binding.btnFindRoomNext.setOnClickListener {
            val bundle = bundleOf("checked_cammate" to "익명의 고양이 1")
            findNavController().navigate(R.id.action_findRoomFragment_to_enterRoomFragment, bundle)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // 블루투스 시스템 설정 화면으로 가는 요청
                val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(enableBTIntent)
            } else {
                showToast("Permission denied")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun enableBT(){
        val permissions: Array<String> = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_ADVERTISE
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
        val missingPermissions = permissions.filter {
            ActivityCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this.requireActivity(), missingPermissions.toTypedArray(), 1)
        } else {
            if (!bluetoothAdapter.isEnabled) {
                enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            } else {
                showToast("Bluetooth is already on")
            }
        }
    }

    private fun initRecyclerView() {
        adapter = CammatesAdapter(cammates)
        binding.rvCammateList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvCammateList.adapter = adapter
    }

    private fun showToast(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}