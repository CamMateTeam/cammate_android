package com.example.cammate.network.data.config

import com.example.cammate.network.data.api.CammateAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var api : CammateAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.125.228.93:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CammateAPI::class.java)
    }
}