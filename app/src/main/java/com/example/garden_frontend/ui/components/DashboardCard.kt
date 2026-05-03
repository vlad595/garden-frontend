package com.example.garden_frontend.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.garden_frontend.ui.theme.GardenfrontendTheme

data class DashboardData(
    val totalPlants: Int,
    val sickPlants: Int,
    val careProducts: Int,
    val harvestHistory: List<Float>,
    val expenseHistory: List<Float>
)

@Composable
fun DashBoardCard(
    modifier: Modifier = Modifier,
    data: DashboardData
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Огляд саду",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    title = "Всі рослини",
                    value = data.totalPlants.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                StatItem(
                    title = "Хворі",
                    value = data.sickPlants.toString(),
                    color = MaterialTheme.colorScheme.error
                )
                StatItem(
                    title = "Засоби",
                    value = data.careProducts.toString(),
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Динаміка врожаю (останні місяці)",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniBarChart(
                data = data.harvestHistory,
                barColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Витрати (останні місяці)",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniBarChart(
                data = data.expenseHistory,
                barColor = MaterialTheme.colorScheme.error.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun StatItem(title: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MiniBarChart(data: List<Float>, barColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { value ->
            val safeValue = value.coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 2.dp)
                    .fillMaxHeight(safeValue)
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    .background(barColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashBoardCardPreview() {
    GardenfrontendTheme {
        val mockData = DashboardData(
            totalPlants = 42,
            sickPlants = 3,
            careProducts = 12,
            harvestHistory = listOf(0.2f, 0.4f, 0.3f, 0.7f, 0.9f, 1.0f),
            expenseHistory = listOf(0.8f, 0.5f, 0.2f, 0.4f, 0.3f, 0.1f)
        )

        DashBoardCard(
            modifier = Modifier.padding(16.dp),
            data = mockData
        )
    }
}