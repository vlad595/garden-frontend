package com.example.garden_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.garden_frontend.ui.theme.GardenfrontendTheme
import com.example.garden_frontend.ui.screens.Main

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GardenfrontendTheme {
                Main()
            }
        }
    }
}