package com.example.garden_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.garden_frontend.ui.components.BottomBar
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.theme.GardenfrontendTheme

@Composable
fun Main(){
    Scaffold(
        topBar = {
            TopBar()
        },

        bottomBar = {
            BottomBar()
        }
    ) { innerPadding ->
        HomeScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun HomeScreen(modifier: Modifier){
    Column(

    ) {
        Text(
            text = "Home"
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun MainPreview(){
    GardenfrontendTheme() {
        Main()
    }
}