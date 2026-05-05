package com.example.garden_frontend.data.api.dto

import android.R
import com.google.gson.annotations.SerializedName

data class PlantModel(
    val name: String,
    val species: String,
    val status: PlantStatus,
    val type: String,
    val id: Int
)

data class BerryBushCreation(
    val name: String,
    val species: String,
    val plantedAt: String,
    val status: Int,
    val trellisNeeds: Boolean
)

data class FruitTreeCreation(
    val name: String,
    val species: String,
    val plantedAt: String,
    val status: Int,
    val height: Int
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