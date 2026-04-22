package com.example.garden_frontend.ui.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.garden_frontend.R

@Composable
fun TopBar(){
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
                onClick = {/*TODO*/}
            ) {
                Icon(
                    painter = painterResource(R.drawable.account_circle_40px),
                    contentDescription = "",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun BottomBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp)
            .shadow(16.dp, RoundedCornerShape(64.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ){
        Button(
            onClick = {/*TODO*/},
            modifier = Modifier.weight(1f).padding(vertical = 6.dp, horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.psychiatry_24px),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Home",
                    maxLines = 1
                )
            }
        }

        Button(
            onClick = {/*TODO*/},
            modifier = Modifier.weight(1f).padding(vertical = 6.dp, horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.psychiatry_24px),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Home"
                )
            }
        }

        Button(
            onClick = {/*TODO*/},
            modifier = Modifier.weight(1f).padding(vertical = 6.dp, horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.psychiatry_24px),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Home"
                )
            }
        }

        Button(
            onClick = {/*TODO*/},
            modifier = Modifier.weight(1f).padding(vertical = 6.dp, horizontal = 4.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.psychiatry_24px),
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Home"
                )
            }
        }
    }
}