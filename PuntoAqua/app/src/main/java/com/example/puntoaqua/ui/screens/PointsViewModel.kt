package com.example.puntoaqua.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puntoaqua.data.ConnectivityState
import com.example.puntoaqua.data.PuntoAquaUiState
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.repositories.UserStateRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class PointsViewModel(
    private val userDbRepository: UserDbRepository
) : ViewModel() {

    val uiState: StateFlow<PuntoAquaUiState> = UserStateRepository.uiState

    var pointsCounter by mutableStateOf("")
    var uid by mutableStateOf("")

    val promoState = mutableStateOf(false)

    val errorState = mutableStateOf(false)

    fun updateIdTextField(newId: String) {
        uid = newId
    }

    fun updatePointsCounter(newPointsCounter: String) {
        pointsCounter = newPointsCounter
    }

    fun updatePoints(uId: String, points: String) {
        viewModelScope.launch {
            try {
                userDbRepository.updatePoints(uId, points)
                val updatedUserInfo = userDbRepository.getUser(uid)
                val decUser = Json.decodeFromString<Map<String,String?>>(updatedUserInfo)
                val prize = decUser.get("Prize") ?: 0
                if(prize == "1") {
                    promoState.value = true
                }
                pointsCounter = ""
                uid = ""
            } catch(e: Exception) {
                errorState.value = true
                pointsCounter = ""
                uid = ""
            }
        }
    }
}