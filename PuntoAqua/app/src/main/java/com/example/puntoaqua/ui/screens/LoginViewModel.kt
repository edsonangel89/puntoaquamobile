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
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.ui.PuntoAquaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel(private val userDbRepository: UserDbRepository) : ViewModel() {

    var username by mutableStateOf("")
    var userpassword by mutableStateOf("")

    val _uiState = MutableStateFlow(PuntoAquaUiState())
    val uiState: StateFlow<PuntoAquaUiState> = _uiState.asStateFlow()

    fun updateUserName(user: String) {
        username = user
    }

    fun updatePassword(password: String) {
        userpassword = password
    }

    fun submitLogin() {
        viewModelScope.launch {
            userDbRepository.login(username, userpassword)
            username = ""
            userpassword = ""
        }
    }

    fun logout() {
    }

    companion object {
        val factory : ViewModelProvider.Factory =
        viewModelFactory {
              initializer {
                  val application = (this[APPLICATION_KEY] as PuntoAquaApplication)
                  val userDbRepository = application.container.userDbRepository
                  LoginViewModel(userDbRepository)
              }
        }
    }
}
