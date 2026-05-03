package com.example.garden_frontend.ui.screens.plantCreation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.garden_frontend.R
import com.example.garden_frontend.data.api.dto.BerryBushCreation
import com.example.garden_frontend.data.api.dto.FruitTreeCreation
import com.example.garden_frontend.data.api.dto.PlantStatus
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.screens.home.ScreenState
import com.example.garden_frontend.utils.TokenManager
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCreationScreen(modifier: Modifier = Modifier, navController: NavController, tokenManager: TokenManager) {
    val viewModel: PlantCreationViewModel = viewModel()

    val state by viewModel.state.collectAsState()
    val currentForm by viewModel.form.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }

    var plantedAt by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    var expandedStatusMenu by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(PlantStatus.Здоровий) }

    var trellisNeeds by remember { mutableStateOf(false) }
    var height by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state == ScreenState.Success) {
            Toast.makeText(context, "Рослину успішно додано!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->

                        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                            timeZone = TimeZone.getTimeZone("UTC")
                        }
                        plantedAt = formatter.format(Date(millis))
                    }
                    showDatePicker = false
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Скасувати")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopBar(iconOnClick = {navController.popBackStack()}, painterResource(R.drawable.arrow_back_ios_24dp))
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Нова рослина",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            item {
                TabRow(
                    selectedTabIndex = if (currentForm is CreationForm.BerryBushCreationForm) 0 else 1,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                ) {
                    Tab(
                        selected = currentForm is CreationForm.BerryBushCreationForm,
                        onClick = { viewModel.changeForm(CreationForm.BerryBushCreationForm) },
                        text = { Text("Ягідний кущ") }
                    )
                    Tab(
                        selected = currentForm is CreationForm.FruitTreeCreationForm,
                        onClick = { viewModel.changeForm(CreationForm.FruitTreeCreationForm) },
                        text = { Text("Фруктове дерево") }
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Назва рослини (напр. Малина 'Полка')") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = species,
                    onValueChange = { species = it },
                    label = { Text("Вид (напр. Rubus idaeus)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            item {
                OutlinedTextField(
                    value = plantedAt,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Дата посадки (РРРР-ММ-ДД)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                painter = painterResource(R.drawable.calendar_month_24dp),
                                contentDescription = "Вибрати дату",
                            )
                        }
                    }
                )
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expandedStatusMenu,
                    onExpandedChange = { expandedStatusMenu = !expandedStatusMenu }
                ) {
                    OutlinedTextField(
                        value = selectedStatus.name,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Статус здоров'я") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatusMenu) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStatusMenu,
                        onDismissRequest = { expandedStatusMenu = false }
                    ) {
                        PlantStatus.values().forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status.name) },
                                onClick = {
                                    selectedStatus = status
                                    expandedStatusMenu = false
                                }
                            )
                        }
                    }
                }
            }

            item {
                when (currentForm) {
                    is CreationForm.BerryBushCreationForm -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Потребує опори (шпалери)?", fontSize = 16.sp)
                            Switch(
                                checked = trellisNeeds,
                                onCheckedChange = { trellisNeeds = it }
                            )
                        }
                    }
                    is CreationForm.FruitTreeCreationForm -> {
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Висота (см)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )
                    }
                }
            }

            if (state is ScreenState.Error) {
                item {
                    Text(
                        text = (state as ScreenState.Error).ErrorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val token = tokenManager.getToken() ?: return@Button

                        when (currentForm) {
                            is CreationForm.BerryBushCreationForm -> {
                                val newBush = BerryBushCreation(name, species, plantedAt + "T06:46:40.622Z", selectedStatus.ordinal, trellisNeeds)
                                viewModel.PostBerryBush(token, newBush) { /* Успіх обробляється в LaunchedEffect */ }
                            }
                            is CreationForm.FruitTreeCreationForm -> {
                                val parsedHeight = height.toIntOrNull() ?: 0
                                val newTree = FruitTreeCreation(name, species, plantedAt + "T06:46:40.622Z", selectedStatus.ordinal, parsedHeight)
                                viewModel.PostFruitTree(token, newTree) { /* Успіх обробляється в LaunchedEffect */ }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = state != ScreenState.Loading && name.isNotBlank() && species.isNotBlank()
                ) {
                    if (state == ScreenState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Створити рослину", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}