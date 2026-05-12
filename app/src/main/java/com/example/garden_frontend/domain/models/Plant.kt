package com.example.garden_frontend.domain.models

interface Plant {
    val id: Int
    val name: String
    val species: String
    val harvests: List<Harvest>
    val plantedAt: String

    fun getPlantDescription(): String
}

data class FruitTree(
    override val id: Int,
    override val name: String,
    override val species: String,
    override val harvests: List<Harvest>,
    override val plantedAt: String,
    val height: Double
) : Plant {

    override fun getPlantDescription(): String {
        return "Фруктове дерево: $name ($species), висота: $height м"
    }
}

data class BerryBush(
    override val id: Int,
    override val name: String,
    override val species: String,
    override val harvests: List<Harvest> =  mutableListOf<Harvest>(),
    override val plantedAt: String,
    val trellisNeeds: Boolean = false
) : Plant {

    override fun getPlantDescription(): String {
        val trellisInfo = if (trellisNeeds) "потребує опори" else "без опори"
        return "Ягідний кущ: $name ($species), $trellisInfo"
    }
}