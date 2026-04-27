package com.example.garden_frontend.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.garden_frontend.ui.screens.Main
import com.example.garden_frontend.ui.screens.account.AccountScreen
import com.example.garden_frontend.ui.screens.auth.AuthScreen
import com.example.garden_frontend.utils.TokenManager

@Composable
fun NavSetup(navController: NavHostController, startDestination: String, tokenManager: TokenManager){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Login.route){
            AuthScreen(tokenManager, navController)
        }
        composable(route = Screen.Home.route){
            Main(navController)
        }
        composable(route = Screen.Account.route){
            AccountScreen(tokenManager, navController)
        }
    }
}