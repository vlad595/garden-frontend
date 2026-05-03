package com.example.garden_frontend.ui.screens.plantCreation

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden_frontend.data.api.Client
import com.example.garden_frontend.data.api.dto.BerryBushCreation
import com.example.garden_frontend.data.api.dto.FruitTreeCreation
import com.example.garden_frontend.domain.models.BerryBush
import com.example.garden_frontend.domain.models.FruitTree
import com.example.garden_frontend.ui.screens.home.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CreationForm {
    object BerryBushCreationForm : CreationForm()
    object FruitTreeCreationForm : CreationForm()
}

class PlantCreationViewModel: ViewModel() {
    private val _state = MutableStateFlow<ScreenState>(ScreenState.Idle)
    var state: StateFlow<ScreenState> = _state

    private val _form = MutableStateFlow<CreationForm>(CreationForm.BerryBushCreationForm)
    var form: StateFlow<CreationForm> = _form

    fun changeForm(form: CreationForm){
        _form.value = form
    }

    fun PostBerryBush(token: String, berryBush: BerryBushCreation, onSuccess: (BerryBush) -> Unit){
        _state.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val response = Client.api.postBerryBush("Bearer $token", berryBush)

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }
                else{
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }

    fun PostFruitTree(token: String, fruitTree: FruitTreeCreation, onSuccess: (FruitTree) -> Unit){
        _state.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val response = Client.api.postFruitTree("Bearer $token", fruitTree)

                if (response.isSuccessful && response.body() != null){
                    _state.value = ScreenState.Success
                    onSuccess(response.body()!!)
                }
                else{
                    _state.value = ScreenState.Error("Помилка відповіді: ${response.code()}")
                }
            } catch (e: Exception){
                _state.value = ScreenState.Error("Помилка з'єднання: ${e.localizedMessage}")
            }
        }
    }
}