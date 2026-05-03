package com.example.garden_frontend.data.api.dto

import com.google.gson.annotations.SerializedName

data class PlantModel(
    val name: String,
    val species: String,
    val status: PlantStatus,
    val type: String
)

enum class PlantStatus{
    @SerializedName("0")
    Здоровий,
    @SerializedName("1")
    Хворий,
    @SerializedName("2")
    Лікуваний,
    @SerializedName("3")
    Мертвий
}