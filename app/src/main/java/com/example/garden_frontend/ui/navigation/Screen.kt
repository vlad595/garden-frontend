package com.example.garden_frontend.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Account : Screen("account")
    object PlantCreation : Screen("plantCreation")
    object PlantInf : Screen("plant_inf/{plantId}/{isTree}") {
        fun createRoute(plantId: Int, isTree: Boolean) = "plant_inf/$plantId/$isTree"
    }
}