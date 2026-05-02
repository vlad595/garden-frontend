package com.example.garden_frontend.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden_frontend.data.api.Client
import com.example.garden_frontend.data.api.dto.PlantModel
import com.example.garden_frontend.ui.screens.account.AccountState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ScreenState{
    object Idle : ScreenState()
    object Loading : ScreenState()
    object Success : ScreenState()
    data class Error(val ErrorMessage: String = "") : ScreenState()
}

class HomeViewModel : ViewModel(){
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var state: StateFlow<ScreenState> = _state

    fun GetPlants(token: String, onSuccess: (List<PlantModel>) -> Unit){
        _state.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val response = Client.api.allPlants("Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }else{
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception) {
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }
}