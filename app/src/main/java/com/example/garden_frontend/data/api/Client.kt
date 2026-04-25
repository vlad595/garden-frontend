package com.example.garden_frontend.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private const val URL = "https://1d3w824m-5164.euw.devtunnels.ms/"

    val api: GardenApi by lazy {
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GardenApi::class.java)
    }
}