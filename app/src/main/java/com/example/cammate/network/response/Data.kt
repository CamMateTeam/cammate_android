package com.example.cammate.network.response

data class Data(
    val createdAt: String,
    val deletedAt: String,
    val id: Int,
    val macAddress: String,
    val nickname: String,
    val roomCode: String,
    val roomId: String
)