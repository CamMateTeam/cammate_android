package com.example.cammate.webRTC.utils

import com.example.cammate.webRTC.Models.MessageModel

interface NewMessageInterface {
    fun onNewMessage(message: MessageModel)
}