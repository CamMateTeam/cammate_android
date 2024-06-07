package com.example.cammate.presentation.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.cammate.databinding.CallReceivedDialogBinding


class CallReceivedDialog() : DialogFragment() {
    private var _binding: CallReceivedDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CallReceivedDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        // 레이아웃 배경을 투명하게 해줌, 필수 아님
        //binding.confirmTextView.text = "$opp 님이 방에 입장하려고 합니다"
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.callDenyButton.setOnClickListener {
            val sendData = Bundle().also {
                it.putString("accept", "false")
            }
            dismiss()    // 대화상자를 닫는 함수
        }
        binding.callAcceptButton.setOnClickListener {
            dismiss()    // 대화상자를 닫는 함수
            val sendData = Bundle().also {
                it.putString("accept", "true")
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}