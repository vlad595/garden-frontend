package com.example.garden_frontend.ui.screens.auth

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.garden_frontend.ui.components.AuthTextField
import com.example.garden_frontend.ui.navigation.Screen
import com.example.garden_frontend.ui.theme.GardenfrontendTheme
import com.example.garden_frontend.utils.TokenManager

@Composable
fun AuthScreen(tokenManager: TokenManager, navController: NavHostController){
    val viewModel: AuthViewModel = viewModel()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.tertiary)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.padding(horizontal = 24.dp),
            shape = RoundedCornerShape(32.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AuthCardContent(viewModel, navController, tokenManager)
            }
        }
    }
}

@Composable
fun AuthCardContent(viewModel: AuthViewModel, navController: NavHostController, tokenManager: TokenManager){
    val currentForm by viewModel.authForm.collectAsState()
    val currentState by viewModel.authState.collectAsState()

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (currentForm == AuthForm.Register){
        Text(
            text = "Реєстрація",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        AuthTextField(label = "Name", value = name, onValueChange = { name = it})
        AuthTextField(label = "Surname", value = surname, onValueChange = { surname = it})
        AuthTextField(label = "Email", value = email, onValueChange = { email = it})
        AuthTextField(label = "Password", value = password, onValueChange = { password = it})

        Button(
            onClick = {
                viewModel.Register(name, surname, email, password, onSuccess = { token ->
                    navController.navigate(Screen.Home.route)
                    tokenManager.saveToken(token)
                })
            }
        ) {
            Text(
                text = "Confirm"
            )
        }

        if (currentState is AuthState.Error) {
            Text(
                text = (currentState as AuthState.Error).ErrorMessage,
                color = Color.Red
            )
        }

        TextButton(
            onClick = {viewModel.ChangeForm(AuthForm.Login)}
        ) {
            Text(
                text = "I already have an account, login ->"
            )
        }
    }
    else {
        Text(
            text = "Вхід",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        AuthTextField(label = "Email", value = email, onValueChange = { email = it})
        AuthTextField(label = "Password", value = password, onValueChange = { password = it})

        Button(
            onClick = {
                viewModel.Login(email, password, onSuccess = {
                    token -> navController.navigate(Screen.Home.route)
                    tokenManager.saveToken(token)
                })
            }
        ) {
            Text(
                text = "Confirm"
            )
        }

        if (currentState is AuthState.Error) {
            Text(
                text = (currentState as AuthState.Error).ErrorMessage,
                color = Color.Red
            )
        }

        TextButton(
            onClick = {viewModel.ChangeForm(AuthForm.Register)}
        ) {
            Text(
                text = "I do not have an account. Register -> "
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AuthPreview(){
    GardenfrontendTheme() {
        val nav = rememberNavController()
        val context: Context = LocalContext.current
        val tokenManager: TokenManager = TokenManager(context)
        AuthScreen(tokenManager, nav)
    }
}