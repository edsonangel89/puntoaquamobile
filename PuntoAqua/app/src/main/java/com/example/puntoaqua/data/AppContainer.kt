package com.example.puntoaqua.data

import android.content.Context
import com.example.puntoaqua.network.UserApiService
import com.example.puntoaqua.repositories.UserDbRepository
import com.example.puntoaqua.repositories.UserDbRepositoryImpl

interface AppContainer {
    val userDbRepository: UserDbRepository
}

class AppDataContainer() : AppContainer {

    override val userDbRepository: UserDbRepository by lazy {
        UserDbRepositoryImpl(UserApiService())
    }

}