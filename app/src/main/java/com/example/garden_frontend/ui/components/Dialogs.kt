package com.example.garden_frontend.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.garden_frontend.domain.models.ProcessingMethods

@Composable
fun AddHarvestDialog(onDismiss: () -> Unit, onConfirm: (Double, Int) -> Unit) {
    var weightInput by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf(ProcessingMethods.FRESH) }

    AlertDialog(
        onDismissRequest = onDismiss,
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