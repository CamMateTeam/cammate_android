package com.example.cammate.network.data

data class CammateRoom(
    val createdAt: String,
    val deletedAt: String,
    val id: Int,
    val macAddress: String,
    val nickname: String,
    val roomCode: String,
    val roomId: String
)