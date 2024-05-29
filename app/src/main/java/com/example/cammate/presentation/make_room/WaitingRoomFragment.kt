package com.example.cammate.presentation.make_room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cammate.databinding.FragmentWaitingRoomMakeBinding


class WaitingRoomFragment : Fragment() {
    private var _binding: FragmentWaitingRoomMakeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaitingRoomMakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nickname = arguments?.getString("nickname")
        val password = arguments?.getString("password")
        binding.waitingTextMake.text = "비밀번호 : $password \n $nickname 님의 방에 사람이 들어올 대까지 대기중입니다"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}