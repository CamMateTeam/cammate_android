package com.example.cammate.webRTC.Models

import com.google.gson.annotations.SerializedName

data class MessageModel(
    val type: String,
    val name: String? = null,
    val target: String? = null,
    val data:Any?=null
)

