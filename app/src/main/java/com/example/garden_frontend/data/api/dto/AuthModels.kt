package com.example.garden_frontend.data.api.dto

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
