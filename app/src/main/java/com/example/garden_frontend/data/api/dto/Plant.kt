package com.example.garden_frontend.data.api.dto

data class PlantModel(
    val name: String,
    val species: String,
    val status: PlantStatus,
    val type: String
)

enum class PlantStatus{
    healthy,
    sick,
    Treated,
    Dead
}