package com.example.puntoaqua.repositories

import com.example.puntoaqua.data.PuntoAquaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object UserStateRepository {

    val _uiState = MutableStateFlow(PuntoAquaUiState())
    val uiState: StateFlow<PuntoAquaUiState> get() = _uiState

    fun setUser(
        isUserLogged: Boolean,
        userId: String,
        userFname: String,
        userLname: String,
        email: String,
        role: String,
        token: String
    ) {
        _uiState.update { cState ->
            cState.copy(
                isUserLogged = isUserLogged,
                userId = userId,
                userFname = userFname,
                userLname = userLname,
                email = email,
                role = role,
                token = token
            )
        }
    }

    fun getToken(): String {
        return _uiState.value.token
    }

    fun setUserLoggedState(logState: Boolean) {
        _uiState.update { cState ->
            cState.copy(
                isUserLogged = logState
            )
        }
    }

}