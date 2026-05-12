package com.example.garden_frontend.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.garden_frontend.ui.screens.home.Main
import com.example.garden_frontend.ui.screens.account.AccountScreen
import com.example.garden_frontend.ui.screens.auth.AuthScreen
import com.example.garden_frontend.ui.screens.plant.PlantScreen
import com.example.garden_frontend.ui.screens.plantCreation.PlantCreationScreen
import com.example.garden_frontend.utils.TokenManager

@RequiresApi(Build.VERSION_CODES.O)
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
            Main(navController, tokenManager)
        }
        composable(route = Screen.Account.route){
            AccountScreen(tokenManager, navController)
        }
        composable(route = Screen.PlantCreation.route){
            PlantCreationScreen(navController = navController, tokenManager = tokenManager)
        }
        composable(
            route = Screen.PlantInf.route,
            arguments = listOf(
                navArgument("plantId") { type = NavType.IntType},
                navArgument("isTree") { type = NavType.BoolType}
            )
        ){ backStackEntry ->
            val plantId = backStackEntry.arguments?.getInt("plantId") ?: 0
            val isTree = backStackEntry.arguments?.getBoolean("isTree") ?: true

            PlantScreen(navController, tokenManager, plantId, isTree)
        }
    }
}