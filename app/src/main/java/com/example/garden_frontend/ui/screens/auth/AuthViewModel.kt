package com.example.garden_frontend.ui.screens.auth

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.resolveDefaults
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.garden_frontend.data.api.Client
import com.example.garden_frontend.domain.models.UserLoginRequest
import com.example.garden_frontend.domain.models.UserRegRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val ErrorMessage: String = "") : AuthState()
}

sealed class AuthForm {
    object Register : AuthForm()
    object Login : AuthForm()
}

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _authForm = MutableStateFlow<AuthForm>(AuthForm.Register)
    val authForm: StateFlow<AuthForm> = _authForm

    fun ChangeForm(form: AuthForm){
        _authForm.value = form
    }

    fun Login(email: String, password: String, onSuccess: (String) -> Unit){
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                val request = UserLoginRequest(email, password)
                val response = Client.api.login(request)

                if (response.isSuccessful && response.body() != null){
                    val token = response.body()!!.token
                    if (token != null){
                        _authState.value = AuthState.Success
                        onSuccess(token)
                    } else{
                        _authState.value = AuthState.Error("Токен нульовий")
                    }
                } else {
                    _authState.value = AuthState.Error("Помилка логіну: ${response.code()}")
                }
            }
            catch (e: Exception){
                _authState.value = AuthState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun Register(name: String, surname: String, email: String, password: String, onSuccess: (String) -> Unit){
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                val request = UserRegRequest(name, surname, email, password)
                val response = Client.api.register(request)

                if (response.isSuccessful && response.body() != null){
                    val token = response.body()!!.token

                    if (token != null){
                        _authState.value = AuthState.Success
                        onSuccess(token)
                    }else {
                        _authState.value = AuthState.Error("Токен нульовий")
                    }
                }else {
                    _authState.value = AuthState.Error("Помилка логіну: ${response.code()}")
                }
            }catch (e: Exception){
                _authState.value = AuthState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }
}