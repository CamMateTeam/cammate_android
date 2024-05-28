package com.example.cammate.retrofit.PostRoom

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostRoom {
    @POST("/api/v1/room")
    fun postRequest(@Body roominfo: PostRequest): Call<PostResponse>
}