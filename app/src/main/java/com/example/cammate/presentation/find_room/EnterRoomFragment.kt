package com.example.cammate.presentation.find_room

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cammate.databinding.FragmentEnterRoomBinding


class EnterRoomFragment : Fragment() {
    private var _binding: FragmentEnterRoomBinding? = null
    private val binding get() = _binding!!

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

        val nickname = requireArguments().getString("selected_cammate")
        binding.tvRoomName.text = "'" + nickname + "' 님의 방으로 입장하기"

        binding.backBtn.setOnClickListener {
            // requireActivity().supportFragmentManager.popBackStack()
            findNavController().navigateUp()
            Log.d("tag", "백버튼 클릭")
        }

        //binding.buttonFirst.setOnClickListener{

        //}

/*        view.findViewById<Button>(R.id.btn).setOnClickListener{
           *//* try {
                //findNavController().navigate(R.id.action_enterRoomFragment_to)
            } catch (e: Exception){
                //Log.d("tag", "${e.message}")
            }*//*
            val dialog = NetworkDialog()
            dialog.show((activity as AppCompatActivity).supportFragmentManager, "NetworkDialog")
        }*/
    }
}