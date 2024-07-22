package com.example.puntoaqua.repositories

import com.example.puntoaqua.network.UserApiService
import kotlinx.serialization.json.Json

interface UserDbRepository {

    suspend fun login(userName: String, password: String): String

    suspend fun logout(uid: String)

    suspend fun signUp(fName: String, lName: String, email: String, password: String): Json

}

class UserDbRepositoryImpl(private val userApiService: UserApiService) : UserDbRepository {

    override suspend fun login(userName: String, password: String): String = userApiService.login(userName,password)

    override suspend fun logout(uid: String) = userApiService.logout(uid)

    override suspend fun signUp(fName: String, lName: String, email: String, password: String) =
        userApiService.signUp(fName,lName,email,password)

}