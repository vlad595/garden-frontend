package com.example.garden_frontend.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.example.garden_frontend.domain.models.UserReturnResponse
import com.example.garden_frontend.domain.models.UserLoginRequest
import com.example.garden_frontend.domain.models.UserRegRequest

interface GardenApi {
    @POST("api/Auth/login")
    suspend fun login(@Body request: UserLoginRequest): Response<UserReturnResponse>

    @POST("api/Auth/register")
    suspend fun register(@Body request: UserRegRequest): Response<UserReturnResponse>
}