package com.example.garden_frontend.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.garden_frontend.R
import com.example.garden_frontend.data.api.dto.PlantModel
import com.example.garden_frontend.data.api.dto.PlantStatus
import com.example.garden_frontend.domain.models.BerryBush
import com.example.garden_frontend.domain.models.CareResource
import com.example.garden_frontend.domain.models.Harvest
import com.example.garden_frontend.domain.models.ProcessingMethods
import com.example.garden_frontend.ui.components.BottomBar
import com.example.garden_frontend.ui.components.BushCard
import com.example.garden_frontend.ui.components.DashBoardCard
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.navigation.Screen
import com.example.garden_frontend.ui.screens.auth.AuthState
import com.example.garden_frontend.ui.theme.GardenfrontendTheme
import com.example.garden_frontend.utils.TokenManager
import kotlinx.coroutines.launch
import com.example.garden_frontend.ui.components.DashboardData
import com.example.garden_frontend.ui.components.HarvestItemCard

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
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page){
                0 -> HomeScreen(viewModel = viewModel, tokenManager = tokenManager, innerPadding = innerPadding)
                1 -> GardenScreen(viewModel = viewModel, tokenManager = tokenManager, innerPadding = innerPadding, navController = navController)
                2 -> HarvestsScreen(viewModel = viewModel, tokenManager = tokenManager, innerPadding = innerPadding)
                3 -> CareResourcesScreen(viewModel = viewModel, tokenManager = tokenManager, innerPadding = innerPadding)
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, tokenManager: TokenManager, innerPadding: PaddingValues){
    val state by viewModel.state.collectAsState()
    var lastPlants by remember { mutableStateOf<List<PlantModel>?>(null) }

    LaunchedEffect(key1 = tokenManager.getToken()) {
        viewModel.GetPlants(tokenManager.getToken()!!, onSuccess = { plants ->
            lastPlants = plants
        })
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding() + 16.dp
        )
    ) {
        item {
            Text(
                text = "Home",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.fillMaxWidth().padding(4.dp),
                textAlign = TextAlign.Start
            )
        }
        item {
            Text(
                text = "Останні додані рослини",
                modifier.padding(4.dp)
            )
        }

        item {
            LazyRow(modifier.fillMaxWidth()) {
                if (state == ScreenState.Loading) {
                    item {
                        CircularProgressIndicator(modifier = modifier.padding(16.dp))
                    }
                }
                if (state == ScreenState.Success && lastPlants != null) {
                    items(lastPlants!!.takeLast(4).reversed()) { plants ->
                        BushCard(
                            berryBush = plants,
                            modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 8.dp)
                        )
                    }
                }
                if (state is ScreenState.Error) {
                    item {
                        Text(
                            text = (state as ScreenState.Error).ErrorMessage,
                            color = Color.Red
                        )
                    }
                }
            }
        }

        item {
            if (state == ScreenState.Success && lastPlants != null) {
                DashBoardCard(
                    data = DashboardData(
                        totalPlants = lastPlants!!.size,
                        sickPlants = lastPlants!!.filter { it.status == PlantStatus.Хворий }.size,
                        careProducts = 0,
                        harvestHistory = listOf(0.2f, 0.4f, 0.3f, 0.7f, 0.9f, 1.0f),
                        expenseHistory = listOf(0.8f, 0.5f, 0.2f, 0.4f, 0.3f, 0.1f)
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)
                )
            } else {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun GardenScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel, tokenManager: TokenManager, innerPadding: PaddingValues, navController: NavHostController){
    val state by viewModel.state.collectAsState()
    var Plants by remember { mutableStateOf<List<PlantModel>?>(null) }

    LaunchedEffect(key1 = tokenManager.getToken()) {
        viewModel.GetPlants(token = tokenManager.getToken()!!, onSuccess = { plants -> Plants = plants})
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = innerPadding.calculateTopPadding(),
            bottom = innerPadding.calculateBottomPadding() + 16.dp
        )
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Button(
                onClick = { navController.navigate(Screen.PlantCreation.route) },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Row() {
                    Icon(
                        painter = painterResource(R.drawable.add_24dp),
                        contentDescription = "Add new plant icon icon"
                    )
                    Text(
                        text = "Додати нову рослину"
                    )
                }
            }
        }

        if (state == ScreenState.Success && Plants != null){
            items(Plants!!) { plant ->
                BushCard(berryBush = plant, modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp))
            }
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            if (state == ScreenState.Loading){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            if (state is ScreenState.Error){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.sentiment_dissatisfied_24dp),
                            contentDescription = "",
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = "У вас ще немає рослин. Додайте одну!",
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HarvestsScreen(viewModel: HomeViewModel, tokenManager: TokenManager, innerPadding: PaddingValues) {
    var harvests by remember { mutableStateOf<List<Harvest>?>(null) }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = tokenManager.getToken()) {
        val token = tokenManager.getToken()
        if (token != null) {
            viewModel.GetHarvests(token, onSuccess = { harvestsList -> harvests = harvestsList })
        }
    }

    val groupedHarvests = remember(harvests) {
        harvests?.groupBy { it.plantId }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        when {
            state is ScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            state is ScreenState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_dialog_alert),
                        contentDescription = "Помилка",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = (state as ScreenState.Error).ErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            groupedHarvests.isNullOrEmpty() -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.psychiatry_24px),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primaryContainer
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Врожаю ще немає",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Час збирати перші плоди!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    groupedHarvests.forEach { (plantId, plantHarvests) ->

                        stickyHeader {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Surface(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(
                                        text = "Рослина #$plantId",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }

                        items(plantHarvests) { harvest ->
                            HarvestItemCard(harvest)
                        }

                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }
                }
            }
        }
    }
}
@Composable
fun CareResourcesScreen(viewModel: HomeViewModel, tokenManager: TokenManager, innerPadding: PaddingValues){
    val state by viewModel.state.collectAsState()
    var careRes by remember { mutableStateOf<List<CareResource>?>(null) }

    LazyColumn(

    ) {

    }
}