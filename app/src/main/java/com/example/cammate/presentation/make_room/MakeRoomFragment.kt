package com.example.cammate.presentation.make_room

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cammate.R
import com.example.cammate.databinding.FragmentMakeRoomBinding
import com.example.cammate.retrofit.PostRoom.PostRequest
import com.example.cammate.retrofit.RetrofitWork


class MakeRoomFragment : Fragment() {
    private var _binding: FragmentMakeRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMakeRoomBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreate.setOnClickListener {
            val nickname:String = binding.editName.text.toString()
            val password:String = binding.editPassword.text.toString()
            val tempMac = "0.0.0.0"
            val deviceId = getDeviceUid()
            val sendData = Bundle().also {
                it.putString("nickname", nickname)
                it.putString("password", deviceId) //password로 바꿔야 함
            }
            val roomData = PostRequest(deviceId,nickname,password)
            val retrofitWork = RetrofitWork(roomData)
            retrofitWork.work()
            findNavController().navigate(R.id.action_makeRoomFragment_to_waitingRoomFragment,sendData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDeviceUid(): String {
        val android_id = Settings.Secure.getString(
            context?.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        return android_id
    }

    /*fun getWIFIMAC(): String { // not working
        try {
            val interfaceName = "wlan0"
            val interfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (interf in interfaces) {
                if (!interf.name.equals(interfaceName, ignoreCase = true)) {
                    continue
                }
                val mac = interf.getHardwareAddress() ?: return "nullmac"
                val buffer = StringBuilder()
                for (aMac in mac) {
                    buffer.append(String.format("%02X:", aMac))
                }
                if (buffer.length > 0) {
                    buffer.deleteCharAt(buffer.length - 1)
                }
                return buffer.toString()
            }
        } catch (ignored: Exception) {
        }
        return "00:00:00:00"
    }*/
}