package com.example.garden_frontend.ui.screens.account

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.garden_frontend.R
import com.example.garden_frontend.domain.models.UserReturnResponse
import com.example.garden_frontend.ui.components.TopBar
import com.example.garden_frontend.ui.navigation.Screen
import com.example.garden_frontend.ui.theme.GardenfrontendTheme
import com.example.garden_frontend.utils.TokenManager
import java.util.jar.Attributes

@Composable
fun AccountScreen(tokenManager: TokenManager, navController: NavHostController){
    val viewModel: AccountViewModel = viewModel()
    var userInfo by remember { mutableStateOf<UserReturnResponse?>(null) }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = tokenManager.getToken()) {
        viewModel.GetMyInfo(token = tokenManager.getToken()!!, onSuccess = { user -> userInfo = user })
    }

    Scaffold(
        topBar = { TopBar(iconOnClick = {navController.popBackStack()}, painterResource(R.drawable.arrow_back_ios_24dp)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Інформація про вас",
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Image(

                painter = painterResource(R.drawable.default_avatar_removebg),
                contentDescription = ""
            )

            if (state == AccountState.Success){
                Row {
                    Text(
                        text = userInfo!!.name!!
                    )
                    Text(
                        text = userInfo!!.surname!!
                    )
                }
                Text(
                    text = userInfo!!.email!!
                )
            }

            if (state is AccountState.Error){
                Text(
                    text = (state as AccountState.Error).ErrorMessage,
                    color = Color.Red
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    tokenManager.clearToken()
                    navController.navigate(Screen.Login.route)
                },
                modifier = Modifier.padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = "Logout",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AccountScreenPreview(){
    GardenfrontendTheme {

    }
}