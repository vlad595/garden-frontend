package com.example.garden_frontend.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.garden_frontend.data.api.dto.FertilizerCreation
import com.example.garden_frontend.data.api.dto.PestControlCreation
import com.example.garden_frontend.domain.models.ProcessingMethods

@Composable
fun AddHarvestDialog(onDismiss: () -> Unit, onConfirm: (Double, Int) -> Unit) {
    var weightInput by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf(ProcessingMethods.FRESH) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Новий врожай") },
        text = {
            Column {
                OutlinedTextField(
                    value = weightInput,
                    onValueChange = { weightInput = it },
                    label = { Text("Вага (кг)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Метод обробки:", fontWeight = FontWeight.Bold)
                ProcessingMethods.entries.forEach { method ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = method == selectedMethod,
                            onClick = { selectedMethod = method }
                        )
                        Text(method.getDisplayName())
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val weight = weightInput.toDoubleOrNull() ?: 0.0
                onConfirm(weight, selectedMethod.value)
            }) { Text("Додати") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Скасувати") }
        }
    )
}

@Composable
fun SpendResourceDialog(onDismiss: () -> Unit, onConfirm: (Int, Int) -> Unit) {
    var resourceIdInput by remember { mutableStateOf("") }
    var quantityInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Використати засіб догляду") },
        text = {
            Column {
                OutlinedTextField(
                    value = resourceIdInput,
                    onValueChange = { resourceIdInput = it },
                    label = { Text("ID засобу") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    label = { Text("Кількість для списання") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val rId = resourceIdInput.toIntOrNull() ?: 0
                val qty = quantityInput.toIntOrNull() ?: 0
                onConfirm(rId, qty)
            }) { Text("Підтвердити") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Скасувати") }
        }
    )
}

@Composable
fun DeleteCareResourceDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, type: String, name: String){
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text("Дійсно бажаєте видалити?") },
        text = { Text("Чи дійсно ви бажаєте видалити $type $name?") },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) { Text("Підтвердити") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Скасувати") }
        }
    )
}

@Composable
fun CreateCareResourceDialog(onDismiss: () -> Unit, onCreateFertilizer: (FertilizerCreation) -> Unit, onCreatePestControl: (PestControlCreation) -> Unit) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Добриво", "Від шкідників")

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    var isOrganic by remember { mutableStateOf(false) }
    var nutrients by remember { mutableStateOf("") }

    var targetPest by remember { mutableStateOf("") }
    var waitingDays by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        title = { Text(text = "Новий засіб") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TabRow(selectedTabIndex = selectedTabIndex, containerColor = MaterialTheme.colorScheme.background) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Назва") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Кількість") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Ціна") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                if (selectedTabIndex == 0) {
                    OutlinedTextField(
                        value = nutrients,
                        onValueChange = { nutrients = it },
                        label = { Text("Поживні речовини") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Checkbox(
                            checked = isOrganic,
                            onCheckedChange = { isOrganic = it }
                        )
                        Text("Органічне")
                    }
                } else {
                    OutlinedTextField(
                        value = targetPest,
                        onValueChange = { targetPest = it },
                        label = { Text("Цільовий шкідник") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = waitingDays,
                        onValueChange = { waitingDays = it },
                        label = { Text("Днів очікування") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val q = quantity.toIntOrNull() ?: 0
                    val p = price.toIntOrNull() ?: 0

                    if (selectedTabIndex == 0) {
                        onCreateFertilizer(FertilizerCreation(name, q, p, isOrganic, nutrients))
                    } else {
                        val wd = waitingDays.toIntOrNull() ?: 0
                        onCreatePestControl(PestControlCreation(name, q, p, targetPest, wd))
                    }
                },
                enabled = name.isNotBlank() && quantity.isNotBlank() && price.isNotBlank() &&
                        (if (selectedTabIndex == 0) nutrients.isNotBlank() else targetPest.isNotBlank() && waitingDays.isNotBlank())
            ) {
                Text("Створити")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Скасувати")
            }
        }
    )
}