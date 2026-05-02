package com.example.garden_frontend.data.api

import com.example.garden_frontend.data.api.dto.PlantModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.garden_frontend.data.api.dto.UserReturnResponse
import com.example.garden_frontend.data.api.dto.UserLoginRequest
import com.example.garden_frontend.data.api.dto.UserRegRequest
import com.example.garden_frontend.domain.models.Plant
import retrofit2.http.GET
import retrofit2.http.Header

interface GardenApi {
    @POST("api/Auth/login")
    suspend fun login(@Body request: UserLoginRequest): Response<UserReturnResponse>

    @POST("api/Auth/register")
    suspend fun register(@Body request: UserRegRequest): Response<UserReturnResponse>

    @GET("api/Auth/me")
    suspend fun me(@Header("Authorization") token: String): Response<UserReturnResponse>

    @GET("api/Plants")
    suspend fun allPlants(@Header("Authorization") token: String): Response<List<PlantModel>>

}