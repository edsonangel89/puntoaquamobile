package com.example.puntoaqua.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.forms.submitForm
import io.ktor.client.statement.HttpResponse
import io.ktor.http.parameters
import kotlinx.serialization.json.Json

class UserApiService {

    private var token: String = ""
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
            response.headers.getAll("set-cookie")?.get(1)?.let { it ->
                token = it.substringBefore(";").substringAfter("=")
                /*Log.d("EMITTER", token)*/
            }

        } catch (e: Exception) {

        } finally {
            client.close()
        }
        return token
    }



    fun logout(): Json {
        return Json.Default
    }

    suspend fun signUp(fName: String, lName: String, email: String, password: String) : Json {
        return Json.Default
    }

}