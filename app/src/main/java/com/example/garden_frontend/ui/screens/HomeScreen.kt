package com.example.garden_frontend.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@Composable
fun Main(navController: NavHostController){
    val pagerState = rememberPagerState(pageCount = { 4 })

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(iconOnClick = {navController.navigate(Screen.Account.route)}, painterResource(R.drawable.account_circle_24px))
        },

        bottomBar = {
            BottomBar(currentPage = pagerState.currentPage, onTabSelected = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            })
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) { page ->
            when (page){
                0 -> HomeScreen()
                1 -> Text("Мій садок")
                2 -> Text("Врожай")
                3 -> Text("Засоби догляду")
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
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