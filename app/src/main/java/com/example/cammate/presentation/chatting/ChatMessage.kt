package com.example.cammate.presentation.chatting

import com.google.gson.Gson

data class ChatMessage(
    val type: Int? = 0,
    val message: String,
    val fromUserId: String,
)

fun ChatMessage.toJsonString(): String = Gson().toJson(this).toString()