package com.example.puntoaqua.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puntoaqua.data.PuntoAquaUiState
import com.example.puntoaqua.repositories.UserStateRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.IOException

data class UserDetailsInfoState(
    val userId: String = "",
    val fName: String = "",
    val lName: String = "",
    val clientPoints: String = "0"
)

class UserDetailViewModel(
) : ViewModel() {

    var userId by mutableStateOf("")
    var body: String = ""
    var userInfo: String = ""

    var uiState: StateFlow<PuntoAquaUiState> = UserStateRepository.uiState

    var _userDetailsState = MutableStateFlow(UserDetailsInfoState())
    var userDetailsState: StateFlow<UserDetailsInfoState> = _userDetailsState.asStateFlow()

    fun updateId(uId: String) {
        userId = uId
    }

    fun getUser(uId: String) {
        viewModelScope.launch {
            try {
                userInfo = user(uId)
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

            }
            userId = ""
        }
    }

    suspend fun user(user: String): String {
        val client = HttpClient(OkHttp)
        try {
            val url = "https://www.puntoaqua.com/api/users/get/${userId}"
            val response: HttpResponse = client.get(urlString = url) {
                header(HttpHeaders.Authorization, "Bearer ${UserStateRepository.getToken()}")
            }
            body = response.body<String>()
        } catch (e: IOException) {

        } finally {
            client.close()
        }
        return body
    }
}