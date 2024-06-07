package com.example.cammate.presentation.chatting

import com.google.gson.Gson

data class ChatMessage(
    val type: Int? = 0,
    val message: String,
    val fromUserId: String,
)

data class Message (val userName : String, val messageContent : String, val roomName: String,var viewType : Int)
data class initialData (val userName : String, val roomName : String)
data class SendMessage(val userName : String, val messageContent: String, val roomName: String)

fun ChatMessage.toJsonString(): String = Gson().toJson(this).toString()