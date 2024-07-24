package com.example.puntoaqua.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class UserApiService {

    private var token: String = ""
    private var user: User

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
                token = it.substringAfter(" ")
                Log.d("EMITTER", token)
            }
            val res = response.body<String>()
            val json = Json { ignoreUnknownKeys = true }
            val user: User = json.decodeFromString(res)
            Log.d("FIRST_NAME",user.FirstName)

            /*user.map { it ->
                Log.d("CONTENT TEST", it.toString())
            }*/

        } catch (e: Exception) {

        } finally {
            client.close()
        }
        return token
    }

    suspend fun logout(uid: String) {
        val client = HttpClient(OkHttp)
        try {
            val response: HttpResponse = client.get(
                urlString = "https://www.puntoaqua.com/api/sessions/logout?uid=${uid}"
            )
        } catch (e: Exception) {

        } finally {
            client.close()
        }
    }

    suspend fun signUp(fName: String, lName: String, email: String, password: String) : Json {
        return Json.Default
    }

}

@Serializable
data class User(
    val UserID: String = "",
    val FirstName: String = "",
    val LastName: String = "",
    val Email: String = "",
    val Password: String = "",
    val Points: String? = null,
    val Prize: String = "",
    val Role: String,
    val EmailVerified: Boolean
)