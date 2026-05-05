package com.example.garden_frontend.ui.screens.plant

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.garden_frontend.R
import com.example.garden_frontend.data.api.dto.HarvestCreateRequest
import com.example.garden_frontend.domain.models.Harvest
import com.example.garden_frontend.domain.models.Plant
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.screens.home.ScreenState
import com.example.garden_frontend.utils.TokenManager
import java.time.LocalDateTime
import com.example.garden_frontend.ui.components.SpendResourceDialog
import com.example.garden_frontend.ui.components.AddHarvestDialog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlantScreen(
    navController: NavHostController,
    tokenManager: TokenManager,
    plantId: Int,
    isTree: Boolean
) {
    val viewModel: PlantViewModel = viewModel()
    val screenState by viewModel.state.collectAsState()
    val token = tokenManager.getToken() ?: ""

    var plant by remember { mutableStateOf<Plant?>(null) }
    var harvests by remember { mutableStateOf<List<Harvest>>(emptyList()) }

    var showHarvestDialog by remember { mutableStateOf(false) }
    var showResourceDialog by remember { mutableStateOf(false) }


    LaunchedEffect(plantId) {
        if (isTree) {
            viewModel.getFruitTree(token, { loadedTree ->
                plant = loadedTree
                harvests = loadedTree.harvests
            }, plantId)
        } else {
            viewModel.getBerryBush(token, { loadedBush ->
                plant = loadedBush
                harvests = loadedBush.harvests
            }, plantId)
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                iconOnClick = { navController.popBackStack() },
                painter = painterResource(R.drawable.arrow_back_ios_24dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            when (screenState) {
                is ScreenState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ScreenState.Error -> {
                    Text(
                        text = (screenState as ScreenState.Error).ErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    plant?.let { currentPlant ->
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                PlantInfoCard(currentPlant)
                            }

                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = { showHarvestDialog = true },
                                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                                    ) {
                                        Text("Додати врожай")
                                    }
                                    Button(
                                        onClick = { showResourceDialog = true },
                                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                                    ) {
                                        Text("Догляд")
                                    }
                                }
                            }

                            item {
                                Text(
                                    text = "Історія врожаю:",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (harvests.isEmpty()) {
                                item { Text("Ще немає записів про врожай.") }
                            } else {
                                items(harvests) { harvest ->
                                    HarvestItem(harvest)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showHarvestDialog) {
        AddHarvestDialog(
            onDismiss = { showHarvestDialog = false },
            onConfirm = { weight, methodId ->
                val request = HarvestCreateRequest(
                    plantId = plantId,
                    weightKg = weight,
                    harvestDate = LocalDateTime.now().toString(),
                    processingMethod = methodId
                )

                viewModel.addHarvestToPlant(token, request) { newHarvest ->
                    harvests = harvests + newHarvest
                    showHarvestDialog = false
                }
            }
        )
    }

    if (showResourceDialog) {
        SpendResourceDialog(
            onDismiss = { showResourceDialog = false },
            onConfirm = { resourceId, quantity ->
                viewModel.updCareResQuantity(token, resourceId, quantity) { _ ->
                    showResourceDialog = false
                }
            }
        )
    }
}

@Composable
fun PlantInfoCard(plant: Plant) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = plant.name, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = plant.getPlantDescription(), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Посаджено: ${plant.plantedAt.substringBefore("T")}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun HarvestItem(harvest: Harvest) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Дата: ${harvest.getFormattedDate()}", fontWeight = FontWeight.Bold)
            Text(text = "Вага: ${harvest.getFormattedWeight()}")
            Text(text = "Призначення: ${harvest.methodEnum.getDisplayName()}")
        }
    }
}