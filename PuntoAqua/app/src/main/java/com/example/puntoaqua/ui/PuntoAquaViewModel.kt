package com.example.puntoaqua.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.puntoaqua.PuntoAquaApplication
import com.example.puntoaqua.repositories.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PuntoAquaUiState(
    val isUserLogged: Boolean = false,
    val userId: String = "",
    val username: String = "",
    val token: String = ""
)

sealed interface AquaUiState {

    data class Logged(val user: PuntoAquaUiState) : AquaUiState
    object Unlogged : AquaUiState
    object Loading : AquaUiState

}

class PuntoAquaViewModel(
    private val userPreferencesRepository: LocalRepository
) : ViewModel() {

    val _appUiState = MutableStateFlow(PuntoAquaUiState())
    val appUiState: StateFlow<PuntoAquaUiState> = _appUiState.asStateFlow()

    val userState: Flow<PuntoAquaUiState> =
        userPreferencesRepository.userState.map { isUserLogged ->
            PuntoAquaUiState(isUserLogged)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_0000),
                initialValue = PuntoAquaUiState()
            )

    companion object {
        val factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                    val userPreferencesRepository = application.localRepository
                    PuntoAquaViewModel(userPreferencesRepository)
                }
            }
    }
}

