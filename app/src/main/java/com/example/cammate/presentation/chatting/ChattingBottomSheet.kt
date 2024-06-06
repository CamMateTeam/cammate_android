package com.example.cammate.presentation.chatting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cammate.databinding.FragmentChattingBottomSheetBinding
import com.example.cammate.network.model.Chat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChattingBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChattingBottomSheetBinding
    private lateinit var adapter: ChatAdapter
    private val url = "" // 채팅 웹소켓 서버 url
    val client = ""
    var dummyChatList = mutableListOf<Chat>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBottomSheetBinding.inflate(inflater, container, false)

        val roomName = "";
        val userName = "";
        Log.d("ChattingBottomSheet", "방이름: ${roomName}님의 방, 내이름: ${userName}")
        if (roomName != ""){
            try {
                startChat()
            } catch (e: Exception){
                Log.d("ChattingBottomSheet", e.message.toString())
            }
        }

        // 리사이클러뷰 세팅
        adapter = ChatAdapter(dummyChatList)
        binding.rvChatting.adapter = adapter
        binding.rvChatting.layoutManager = LinearLayoutManager(requireContext())

        // 클릭 리스너
        adapter.notifyDataSetChanged()

        return binding.root
    }

    fun startChat(){
        // 클라이언트 연결

        //
    }
}