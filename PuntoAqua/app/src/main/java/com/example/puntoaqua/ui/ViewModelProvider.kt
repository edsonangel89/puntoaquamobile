package com.example.puntoaqua.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PuntoAquaUiState(
    val isUserLogged: Boolean = false,
    val token: String = ""
)

sealed interface AquaUiState {

    data class Logged(val user: PuntoAquaUiState) : AquaUiState
    object Unlogged : AquaUiState
    object Loading : AquaUiState

}

class ViewModelProvider : ViewModel() {

    /*val _appUiState = MutableStateFlow(PuntoAquaUiState())
    val appUiState: StateFlow<PuntoAquaUiState> = _appUiState.asStateFlow()
*/
    var aquaUiState: AquaUiState by mutableStateOf(AquaUiState.Unlogged)
        private set



}

