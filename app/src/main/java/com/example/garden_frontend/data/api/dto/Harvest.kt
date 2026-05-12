package com.example.garden_frontend.data.api.dto

data class HarvestCreateRequest(
    val plantId: Int,
    val weightKg: Double,
    val harvestDate: String,
    val processingMethod: Int
)