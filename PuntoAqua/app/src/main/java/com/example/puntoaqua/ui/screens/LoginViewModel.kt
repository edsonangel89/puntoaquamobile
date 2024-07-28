package com.example.puntoaqua.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puntoaqua.data.PuntoAquaUiState
import com.example.puntoaqua.repositories.LocalRepository
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.repositories.UserStateRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class LoginViewModel(
    private val userDbRepository: UserDbRepository,
    private val userPreferencesRepository: LocalRepository
) : ViewModel() {

    var username by mutableStateOf("")
    var userpassword by mutableStateOf("")

    val uiState: StateFlow<PuntoAquaUiState> = UserStateRepository.uiState

    var errorLogin = mutableStateOf(false)

    fun updateUserName(user: String) {
        username = user
    }

    fun updatePassword(password: String) {
        userpassword = password
    }

    fun submitLogin() {
        viewModelScope.launch {
            try {
                val userJson = userDbRepository.login(username, userpassword)

                when(userJson) {
                    "Non-user" -> throw Exception("Non-user")
                    "Wrong-password" -> throw Exception("Wrong-password")
                    "Non-verified" -> throw Exception("Non-verified")
                }

                val user = Json.decodeFromString<Map<String,String?>>(userJson)
                val uid = user.get("UserID") ?: ""
                val fname = user.get("FirstName") ?: ""
                val lname = user.get("LastName") ?: ""
                val email = user.get("Email") ?: ""
                val role = user.get("Role") ?: ""
                val token = user.get("Token") ?: ""
                userPreferencesRepository.saveToken(token)
                userPreferencesRepository.saveLoginState(true)
                UserStateRepository.setUser(
                    isUserLogged = true,
                    userId = uid,
                    userFname = fname,
                    userLname = lname,
                    email = email,
                    role = role,
                    token = token
                )
            } catch (e: Exception) {
                errorLogin.value = true
            }
            username = ""
            userpassword = ""
        }
    }

    fun logout(uid: String) {
        viewModelScope.launch {
            try {
                userDbRepository.logout(uid)
                userPreferencesRepository.saveLoginState(false)
                UserStateRepository.setUser(
                    isUserLogged = false,
                    userId = "",
                    userFname = "",
                    userLname = "",
                    email = "",
                    role = "",
                    token = ""
                )
            } catch (e: Exception) {
                /*HANDLE EXCEPTION*/
            }
        }
    }
}