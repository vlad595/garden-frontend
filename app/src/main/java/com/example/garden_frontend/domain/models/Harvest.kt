package com.example.garden_frontend.domain.models

enum class ProcessingMethods(val value: Int) {
    FRESH(0),
    JAM(1),
    FROZEN(2);

    fun getDisplayName(): String {
        return when (this) {
            FRESH -> "Свіжі фрукти/ягоди"
            JAM -> "На джем/варення"
            FROZEN -> "Заморожені"
        }
    }
}

data class Harvest(
    val id: Int,
    val plantId: Int,
    val weightKg: Double,
    val harvestDate: String,
    val processingMethod: ProcessingMethods
) {
    fun getFormattedWeight(): String {
        return "%.2f кг".format(weightKg)
    }

    fun getFormattedDate(): String {
        return harvestDate.substringBefore("T")
    }
}