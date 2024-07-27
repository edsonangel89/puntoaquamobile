package com.example.puntoaqua.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puntoaqua.data.PuntoAquaUiState
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.repositories.UserStateRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PointsViewModel(
    private val userDbRepository: UserDbRepository
) : ViewModel() {

    val uiState: StateFlow<PuntoAquaUiState> = UserStateRepository.uiState

    var pointsCounter by mutableStateOf("")
    var uid by mutableStateOf("")

    fun updateIdTextField(newId: String) {
        uid = newId
    }

    fun updatePointsCounter(newPointsCounter: String) {
        pointsCounter = newPointsCounter
    }

    fun updatePoints(uId: String, points: String) {
        viewModelScope.launch {
            val res = userDbRepository.updatePoints(uId, points)
            pointsCounter = ""
            uid = ""
        }
    }
}