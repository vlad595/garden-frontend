package com.example.garden_frontend.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Account : Screen("account")
}