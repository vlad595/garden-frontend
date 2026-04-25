package com.example.garden_frontend.domain.models

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
    val email: String,
    val password: String
)

data class UserReturnResponse(
    val id: Int,
    val name: String?,
    val surname: String?,
    val email: String?,
    val token: String?
)

data class UserRegRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
