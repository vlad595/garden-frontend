package com.example.garden_frontend.domain.models

abstract class CareResource {
    abstract val id: Int
    abstract val name: String
    abstract val quantity: Double
    abstract val price: Double

    fun getFormattedPrice(): String {
        return "%.2f грн".format(price)
    }
}

data class Fertilizer(
    override val id: Int,
    override val name: String,
    override val quantity: Double,
    override val price: Double,
    val npkRatio: String
) : CareResource()

data class PestControl(
    override val id: Int,
    override val name: String,
    override val quantity: Double,
    override val price: Double,
    val toxicityLevel: String
) : CareResource()