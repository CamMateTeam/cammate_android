package com.example.cammate.presentation.find_room

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cammate.R
import com.example.cammate.databinding.FragmentEnterRoomBinding
import com.example.cammate.presentation.utils.animals
import com.example.cammate.presentation.utils.determiners
import java.util.Random


class EnterRoomFragment : Fragment() {
    private var _binding: FragmentEnterRoomBinding? = null
    private val binding get() = _binding!!
    private var userName : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roomName = requireArguments().getString("selected_cammate") // 입장하려는 방 주인 이름
        binding.tvRoomName.text = "'" + roomName + "' 님의 방으로 입장하기"
        /*        roomName = arguments?.getString("roomName")
        binding.enteringText.text = "$roomName 님의 방으로 입장하기"*/

        // 랜덤 이름 체크박스
        binding.checkRandom.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val randomDeterminer = determiners[Random().nextInt(determiners.size)]
                val randomAnimal = animals[Random().nextInt(animals.size)]
                val randomName = "$randomDeterminer $randomAnimal"
                binding.editName.setText(randomName)
            }
        }

        binding.btn.setOnClickListener {
            userName = binding.editName.text.toString() // 입장하려는 사람 이름
            val password: String = binding.editPassword.text.toString()
            if (password != "1234"){
                binding.checkPwd.visibility = View.VISIBLE
            }

            else {
                userName = binding.editName.text.toString() // 입장하려는 사람 이름
                /*            val tempMac = "0.0.0.0"
            val deviceId = getDeviceUid()*/
                val sendData = Bundle().also {
                    it.putString("userName", userName)
                    it.putString("roomName", roomName)
                    it.putString("password", password) //password로 바꿔야 함
                }
                //init()
                /*          val roomData = PostRequest(deviceId, userName!!,password)
            val retrofitWork = RetrofitWork(roomData)
            retrofitWork.work()*/
                findNavController().navigate(R.id.action_EnterRoomFragment_to_WaitingRoomFragmentFind, sendData)

                binding.backBtn.setOnClickListener {
                    // requireActivity().supportFragmentManager.popBackStack()
                    findNavController().navigateUp()
                }

                //binding.buttonFirst.setOnClickListener{

                //}

                /*        view.findViewById<Button>(R.id.btn).setOnClickListener{
           *//* try {
                //findNavController().navigate(R.id.action_enterRoomFragment_to)
            } catch (e: Exception){
                //Log.d("tag", "${e.message}")
            }*/
                /*
            val dialog = NetworkDialog()
            dialog.show((activity as AppCompatActivity).supportFragmentManager, "NetworkDialog")
        }*/
            }
        }
    }
    }

    }
}
