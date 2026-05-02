package com.example.garden_frontend.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.garden_frontend.R
import com.example.garden_frontend.data.api.dto.PlantModel
import com.example.garden_frontend.domain.models.BerryBush
import com.example.garden_frontend.ui.components.BottomBar
import com.example.garden_frontend.ui.components.BushCard
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.navigation.Screen
import com.example.garden_frontend.ui.screens.auth.AuthState
import com.example.garden_frontend.ui.theme.GardenfrontendTheme
import com.example.garden_frontend.utils.TokenManager
import kotlinx.coroutines.launch

@Composable
fun Main(navController: NavHostController, tokenManager: TokenManager){
    val viewModel = HomeViewModel()
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when (page){
                0 -> HomeScreen(viewModel = viewModel, tokenManager = tokenManager)
                1 -> Text("Мій садок")
                2 -> Text("Врожай")
                3 -> Text("Засоби догляду")
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, tokenManager: TokenManager){
    val state by viewModel.state.collectAsState()
    var lastPlants by remember { mutableStateOf<List<PlantModel>?>(null) }

    LaunchedEffect(key1 = tokenManager.getToken()) {
        viewModel.GetPlants(tokenManager.getToken()!!, onSuccess = { plants ->
            lastPlants = plants
        })
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home"
        )
        Text(
            text = "Останні додані рослини"
        )
        LazyRow(modifier.fillMaxWidth()) {
            if (state == ScreenState.Loading){
                item {
                    Spacer(Modifier.weight(1f))
                    CircularProgressIndicator()
                    Spacer(Modifier.weight(1f))
                }
            }
            if (state == ScreenState.Success && lastPlants != null){
                items(lastPlants!!) { plants ->
                    BushCard(berryBush = plants, modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 8.dp))
                }
            }
            if(state is ScreenState.Error){
                item {
                    Text(
                        text = (state as ScreenState.Error).ErrorMessage,
                        color = Color.Red
                    )
                }
            }
        }
    }
}