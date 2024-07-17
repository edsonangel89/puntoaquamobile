package com.example.puntoaqua.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class PointsViewModel : ViewModel() {

    var pointsCounter by mutableStateOf("0")
    var points = pointsCounter.toInt()

    fun updatePointsCounter(newPointsCounter: String) {
        if (newPointsCounter == null) {
            pointsCounter = "0"
        } else {
            pointsCounter = newPointsCounter
        }
    }

    fun updatePoints(points: String) {
        runBlocking {
            withContext(Dispatchers.IO) {

            }
        }
    }
}