package com.example.garden_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.garden_frontend.R
import com.example.garden_frontend.ui.components.BottomBar
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.navigation.Screen
import com.example.garden_frontend.ui.theme.GardenfrontendTheme

@Composable
fun Main(navController: NavHostController){
    Scaffold(
        topBar = {
            TopBar(iconOnClick = {navController.navigate(Screen.Account.route)}, painterResource(R.drawable.account_circle_24px))
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

    }
}