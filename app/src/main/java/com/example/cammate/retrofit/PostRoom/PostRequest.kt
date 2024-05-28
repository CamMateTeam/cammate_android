package com.example.cammate.retrofit.PostRoom

data class PostRequest(
    val macAddress: String,
    val nickname: String,
    val password: String
)