package com.example.cammate.make_room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cammate.R
import com.example.cammate.databinding.FragmentMakeRoomBinding

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
            val sendData = Bundle().also {
                it.putString("nickname", nickname)
                it.putString("password", password)
            }
            findNavController().navigate(R.id.action_makeRoomFragment_to_waitingRoomFragment,sendData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}