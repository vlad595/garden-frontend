package com.example.garden_frontend.data.api.dto


data class CareResourceDto(
    val id: Int,
    val userId: Int,
    val resourceType: String,
    val name: String,
    val quantity: Int,
    val price: Double,
    val createdAt: String,
    val isOrganic: Boolean,
    val nutrients: String,
    val targetPest: String,
    val waitingDays: Int
)