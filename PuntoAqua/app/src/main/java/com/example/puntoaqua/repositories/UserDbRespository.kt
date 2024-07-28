package com.example.puntoaqua.repositories

import com.example.puntoaqua.network.UserApiService

interface UserDbRepository {

    suspend fun login(userName: String, password: String): String

    suspend fun logout(uid: String)

    suspend fun getUserInfo(uid: String): String

    suspend fun updatePoints(uid: String, points: String): String

    suspend fun getUser(uid: String): String

}

class UserDbRepositoryImpl(private val userApiService: UserApiService) : UserDbRepository {

    override suspend fun login(userName: String, password: String) = userApiService.login(userName,password)

    override suspend fun logout(uid: String) = userApiService.logout(uid)

    override suspend fun getUserInfo(uid: String) = userApiService.getUserInfo(uid)

    override suspend fun updatePoints(uid: String, points: String) = userApiService.updatePoints(uid, points)

    override suspend fun getUser(uid: String): String = userApiService.user(uid)

}