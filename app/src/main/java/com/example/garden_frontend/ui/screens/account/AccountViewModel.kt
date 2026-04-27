package com.example.garden_frontend.ui.screens.account

import android.accounts.Account
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden_frontend.data.api.Client
import com.example.garden_frontend.domain.models.UserReturnResponse
import com.example.garden_frontend.ui.screens.auth.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AccountState {
    object Idle : AccountState()
    object Loading : AccountState()
    object Success : AccountState()
    data class Error(val ErrorMessage: String = "") : AccountState()
}

class AccountViewModel : ViewModel() {
    private val _state = MutableStateFlow<AccountState>(AccountState.Idle)
    val state: StateFlow<AccountState> = _state

    fun GetMyInfo(token: String, onSuccess: (UserReturnResponse) -> Unit) {
        _state.value = AccountState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.me("Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = AccountState.Success
                    onSuccess(response.body()!!)
                }else{
                    _state.value = AccountState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch (e: Exception){
                _state.value = AccountState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }
}