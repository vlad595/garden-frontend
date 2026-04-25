package com.example.garden_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.garden_frontend.ui.navigation.NavSetup
import com.example.garden_frontend.ui.navigation.Screen
import com.example.garden_frontend.ui.theme.GardenfrontendTheme
import com.example.garden_frontend.ui.screens.Main
import com.example.garden_frontend.utils.TokenManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GardenfrontendTheme {
                val navController = rememberNavController()
                val context = LocalContext.current

                val tokenManager = remember { TokenManager(context) }

                val startDest = if (tokenManager.getToken() != null) Screen.Home.route else Screen.Login.route

                NavSetup(navController, startDest, tokenManager)
            }
        }
    }
}