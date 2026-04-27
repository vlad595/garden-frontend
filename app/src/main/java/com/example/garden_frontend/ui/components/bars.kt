package com.example.garden_frontend.ui.components

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.garden_frontend.R

@Composable
fun TopBar(iconOnClick: () -> Unit, painter: Painter){
    Column(
        modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier.padding(top = 36.dp, start = 10.dp, end = 10.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.psychiatry_24px),
                contentDescription = "",
                modifier = Modifier.size(36.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = "Organic Garden",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = iconOnClick
            ) {
                Icon(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun BottomBar(currentPage: Int, onTabSelected: (Int) -> Unit){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .shadow(16.dp, RoundedCornerShape(64.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ){
        BottomNavItem("Home", painterResource(R.drawable.home_24dp), { onTabSelected(0) }, modifier = Modifier.weight(0.25f), isSelected = currentPage == 0)
        BottomNavItem("Garden", painterResource(R.drawable.potted_plant_24dp), { onTabSelected(1) }, modifier = Modifier.weight(0.25f), isSelected = currentPage == 1)
        BottomNavItem("Harvests", painterResource(R.drawable.grocery_24dp), { onTabSelected(2) }, modifier = Modifier.weight(0.25f), isSelected = currentPage == 2)
        BottomNavItem("Health", painterResource(R.drawable.health_cross_24dp), { onTabSelected(3) }, modifier = Modifier.weight(0.25f), isSelected = currentPage == 3)
    }
}

@Composable
fun BottomNavItem(name: String, icon: Painter, onClick: () -> Unit, modifier: Modifier = Modifier, isSelected: Boolean = false) {

    Button(
        onClick = onClick,
        modifier = modifier.padding(vertical = 6.dp, horizontal = 4.dp),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = name,
                fontSize = 12.sp,
                maxLines = 1
            )
        }
    }
}