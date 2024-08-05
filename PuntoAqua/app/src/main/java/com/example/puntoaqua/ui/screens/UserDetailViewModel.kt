package com.example.puntoaqua.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puntoaqua.data.ConnectivityState
import com.example.puntoaqua.data.PuntoAquaUiState
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.repositories.UserStateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

data class UserDetailsInfoState(
    val userId: String = "",
    val fName: String = "",
    val lName: String = "",
    val clientPoints: String = "0"
)

class UserDetailViewModel(
    private val userDbRepository: UserDbRepository
) : ViewModel() {

    var userId by mutableStateOf("")
    var userInfo: String = ""

    var uiState: StateFlow<PuntoAquaUiState> = UserStateRepository.uiState

    var _userDetailsState = MutableStateFlow(UserDetailsInfoState())
    var userDetailsState: StateFlow<UserDetailsInfoState> = _userDetailsState.asStateFlow()

    val userExist = mutableStateOf(false)

    fun updateId(uId: String) {
        userId = uId
    }

    fun getUser(uId: String) {
        viewModelScope.launch {
            try {
                userInfo = userDbRepository.getUser(uId)
                val decUser = Json.decodeFromString<Map<String,String?>>(userInfo)
                val userId = decUser.get("UserID") ?: ""
                val fName = decUser.get("FirstName") ?: ""
                val lName = decUser.get("LastName") ?: ""
                val points = decUser.get("Points") ?: "0"
                _userDetailsState.update { currentState ->
                    currentState.copy(
                        userId = userId,
                        fName = fName,
                        lName = lName,
                        clientPoints = points
                    )
                }
            } catch (e: Exception) {
                 userExist.value = true
                _userDetailsState.update { currentState ->
                    currentState.copy(
                        userId = "",
                        fName = "",
                        lName = "",
                        clientPoints = "0"
                    )
                }
            }
            userId = ""
        }
    }

}