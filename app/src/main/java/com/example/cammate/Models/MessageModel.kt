package com.example.cammate.Models

import com.google.gson.annotations.SerializedName

data class MessageModel(
    @SerializedName("status")
    var status: Int,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("message")
    var message: String,
    @SerializedName("data")
    var data:Any?=null
)

