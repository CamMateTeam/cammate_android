package com.example.cammate.network.data.api

import com.example.cammate.network.response.RoomResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CammateAPI {
    @GET("api/v1/room") // baserURL + "macAddress"
    suspend fun getRooms(@Query("macAddress") macAddress: String): RoomResponse
}