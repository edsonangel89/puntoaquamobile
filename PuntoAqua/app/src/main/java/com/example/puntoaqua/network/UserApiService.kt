package com.example.puntoaqua.network

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.puntoaqua.repositories.UserStateRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import kotlinx.coroutines.flow.update
import java.io.IOException


class UserApiService {

    private var user by mutableStateOf("")
    private var userInfo: String by mutableStateOf("")

    private var responseJson: String by mutableStateOf("")

    suspend fun login(username: String, password: String) : String {
        val client = HttpClient(OkHttp)
        try {
            val response: HttpResponse = client.submitForm(
                url = "https://www.puntoaqua.com/api/sessions/login",
                formParameters = parameters {
                    append("email", username)
                    append("password", password)
                }
            )
            response.headers.get("Authorization")?.let { it ->
                val tok = it.substringAfter(" ")
                UserStateRepository._uiState.update { cState ->
                    cState.copy(
                        token = it
                    )
                }
            }
            val userJson = response.body<String>()
            user = userJson

        } catch (e: Exception) {
            Log.e("ERROR_LOGIN",e.toString())
        } finally {
            client.close()
        }
        return user
    }

    suspend fun logout(uid: String) {
        val client = HttpClient(OkHttp)
        try {
            val response: HttpResponse = client.get(
                urlString = "https://www.puntoaqua.com/api/sessions/logout?uid=${uid}"
            )
        } catch (e: Exception) {
            /*HANDLE EXCEPTION*/
        } finally {
            client.close()
        }
    }

    suspend fun getUserInfo(uid: String): String {
        val client = HttpClient(OkHttp)
        try {
            val response: HttpResponse = client.get(
                urlString = "https://www.puntoaqua.com/api/users/get/${uid}"
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer ")
                }
            }
            userInfo = response.body<String>()
        } catch (e: Exception) {
            /*HANDLE EXCEPTION*/
        }
        finally {
            client.close()
        }
        return userInfo
    }

    suspend fun updatePoints(uid: String, points: String): String {
        val client = HttpClient(OkHttp)
        try {
             val response: HttpResponse = client.submitForm (
                 url = "https://www.puntoaqua.com/api/users/update/points/${uid}",
                 formParameters = parameters {
                     append("points", points)
                 }
             )
            {
                 headers {
                      append(HttpHeaders.Authorization, "Bearer ${UserStateRepository.getToken()}")
                 }
            }
            responseJson = response.body<String>()
        } catch (e: Exception) {
            /*HANDLE EXCEPTION*/
        }
        finally {
            client.close()
        }
        return responseJson
    }

    suspend fun user(userId: String): String {
        val client = HttpClient(OkHttp)
        var body: String = ""
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