package com.example.cammate.retrofit

import android.util.Log
import com.example.cammate.retrofit.PostRoom.PostRequest
import com.example.cammate.retrofit.PostRoom.PostResponse
import retrofit2.Call
import retrofit2.Response

class RetrofitWork(private val roomInfo: PostRequest) {
    fun work() {
        val service = RetrofitBuilder.emgMedService

//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
        service.postRequest(roomInfo)
            .enqueue(object : retrofit2.Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("roomSuccess", "$response")
                    }
                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    Log.d("roomFail", t.message.toString())
                }
            })
    }
}