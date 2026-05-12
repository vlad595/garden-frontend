package com.example.garden_frontend.data.api.dto

data class GardenStatisticsDto(
    val totalPlants: Int,
    val sickPlants: Int,
    val treatedPlants: Int,

    val totalHarvestWeightLastMonthKg: Double,
    val harvestsLastMonthCount: Int,
    val frozenFruitsWeightKg: Double,
    val fruitsForConservationWeightKg: Double,
    val soldFruitsWeightLastMonthKg: Double,
    val totalMonthlyProfit: Double,
    val mostFruitfulPlantName: String,

    val totalCareProducts: Int,
    val fertilizerCount: Int,
    val pestControlCount: Int,
    val totalExpenses: Double
)