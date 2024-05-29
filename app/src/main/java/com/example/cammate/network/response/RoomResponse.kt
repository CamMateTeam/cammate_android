package com.example.cammate.network.response

data class RoomResponse(
    val data: List<Data>,
    val message: String,
    val status: String
)