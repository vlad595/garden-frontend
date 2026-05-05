package com.example.garden_frontend.ui.screens.plant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden_frontend.data.api.Client
import com.example.garden_frontend.data.api.dto.CareResourceDto
import com.example.garden_frontend.data.api.dto.HarvestCreateRequest
import com.example.garden_frontend.data.api.dto.PlantModel
import com.example.garden_frontend.domain.models.BerryBush
import com.example.garden_frontend.domain.models.FruitTree
import com.example.garden_frontend.domain.models.Harvest
import com.example.garden_frontend.domain.models.Plant
import com.example.garden_frontend.ui.screens.home.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlantViewModel : ViewModel(){
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Idle)
    val state: StateFlow<ScreenState> = _state

    fun getBerryBush(token: String, onSuccess: (BerryBush) -> Unit, id: Int){
       _state.value = ScreenState.Loading

       viewModelScope.launch {
           try {
                val response = Client.api.getBerryBush(id, "Bearer $token")

               if (response.isSuccessful && response.body() != null){
                   _state.value = ScreenState.Success
                   onSuccess(response.body()!!)
               }
               else {
                   _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
               }
           }catch (e: Exception){
               _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
           }
       }
    }

    fun getFruitTree(token: String, onSuccess: (FruitTree) -> Unit, id: Int){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.getFruitTreeById(id, "Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }
                else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun addHarvestToPlant(token: String, harvest: HarvestCreateRequest, onSuccess: (Harvest) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.postHarvest("Bearer $token", harvest)

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }
                else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun getHarvestsByPlant(token: String, plantId: Int, onSuccess: (Harvest) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.getHarvestsByPlant(plantId, "Bearer $token")

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }
                else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun updCareResQuantity(token: String, id: Int, quantity: Int, onSuccess: (CareResourceDto) -> Unit){
        _state.value = ScreenState.Loading

        viewModelScope.launch {
            try{
                val response = Client.api.updCareResourceQuantity(id, "Bearer $token", quantity)

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }
                else {
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            }catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }
}