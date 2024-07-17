package com.example.puntoaqua.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException

data class UserDetailsInfoState(
    val clientId: Int = 0,
    val clientFullName: String = "",
    val clientPoints: Int = 0
)

class UserDetailViewModel : ViewModel() {

    var userId by mutableStateOf("0")

    fun updateId(uId: String) {
        userId = uId
    }

    fun getUser(uId: String): String {
        var user: String = ""
        runBlocking {
            withContext(Dispatchers.IO) {
                user = user(uId)
            }
        }
        return user
    }

    suspend fun user(user: String): String {
        var body: String = ""
        var token: String = ""
        viewModelScope.launch {
            val client = HttpClient(OkHttp)
            Log.d("DEBUG", user)
            try {
                val response: HttpResponse = client.get("https://www.puntoaqua.com/api/users/get/$user") {
                    headers {
                        append(HttpHeaders.SetCookie, "Authorization=$token")
                    }
                }
                Log.d("DEBUG", response.body())
                body = response.body()
            } catch (e: IOException) {
                Log.e("DEBUG", e.toString())
            } finally {
                client.close()
            }
        }
        return body
    }
}