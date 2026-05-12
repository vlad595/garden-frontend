package com.example.garden_frontend.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden_frontend.data.api.Client
import com.example.garden_frontend.data.api.dto.BerryBushCreation
import com.example.garden_frontend.data.api.dto.CareResourceDto
import com.example.garden_frontend.data.api.dto.FertilizerCreation
import com.example.garden_frontend.data.api.dto.FruitTreeCreation
import com.example.garden_frontend.data.api.dto.GardenStatisticsDto
import com.example.garden_frontend.data.api.dto.PestControlCreation
import com.example.garden_frontend.data.api.dto.PlantModel
import com.example.garden_frontend.domain.models.BerryBush
import com.example.garden_frontend.domain.models.CareResource
import com.example.garden_frontend.domain.models.FruitTree
import com.example.garden_frontend.domain.models.Harvest
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

    fun GetHarvests(token: String, onSuccess: (List<Harvest>) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try {
                val response = Client.api.getAllHarvests("Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }else{
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun GetCareResources(token: String, onSuccess: (List<CareResourceDto>) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.getAllCareResources("Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }else{
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch(e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun PostPestControl(token: String, pestControl: PestControlCreation, onSuccess: (CareResourceDto) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.postPestControl("Bearer $token", pestControl)

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                } else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun PostFertilizer(token: String, fertilizerCreation: FertilizerCreation, onSuccess: (CareResourceDto) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.postFertilizer("Bearer $token", fertilizerCreation)

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun DeleteCareResource(token: String, careResourceId: Int, onSuccess: () -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.deleteCareResource(careResourceId, "Bearer $token")

                if (response.isSuccessful){
                    _state.value = ScreenState.Success
                    onSuccess()
                }else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun GetStatistics(token: String, onSuccess: (GardenStatisticsDto) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.getStats("Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                } else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }
}