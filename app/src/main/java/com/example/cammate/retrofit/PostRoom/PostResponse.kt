package com.example.cammate.retrofit.PostRoom

import com.example.cammate.retrofit.PostRoom.Data
import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String
)