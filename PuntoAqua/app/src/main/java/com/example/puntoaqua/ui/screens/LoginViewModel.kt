package com.example.puntoaqua.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.puntoaqua.PuntoAquaApplication
import com.example.puntoaqua.repositories.LocalRepository
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.ui.PuntoAquaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userDbRepository: UserDbRepository,
    private val userPreferencesRepository: LocalRepository
) : ViewModel() {

    var username by mutableStateOf("")
    var userpassword by mutableStateOf("")

    val _uiState = MutableStateFlow(PuntoAquaUiState())
    val uiState: StateFlow<PuntoAquaUiState> = _uiState.asStateFlow()

    val userId by mutableStateOf(_uiState.value.userId)

    var tokenUiState: String = ""

    fun updateUserName(user: String) {
        username = user
    }

    fun updatePassword(password: String) {
        userpassword = password
    }

    fun submitLogin() {
        viewModelScope.launch {
            val token = userDbRepository.login(username, userpassword)

            userPreferencesRepository.saveToken(token)
            userPreferencesRepository.token.collect { it ->
                tokenUiState = it
                _uiState.update { currentState ->
                    currentState.copy(
                        isUserLogged = true,
                        token = tokenUiState
                    )
                }
            }
        }
        username = ""
        userpassword = ""
    }

    fun logout(uid: String) {
        viewModelScope.launch {
            try {
                val logout = userDbRepository.logout(uid)
                _uiState.update { currentState ->
                    currentState.copy(
                        isUserLogged = false,
                        userId = "",
                        username = "",
                        token = ""
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                    val userDbRepository = application.container.userDbRepository
                    val userPreferencesRepository = application.localRepository
                    LoginViewModel(userDbRepository, userPreferencesRepository)
                }
            }
    }
}