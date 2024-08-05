package com.example.puntoaqua

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.puntoaqua.data.AppDataContainer
import com.example.puntoaqua.data.ConnectivityState
import com.example.puntoaqua.repositories.LocalRepository

private val Context.userInfo: DataStore<Preferences> by preferencesDataStore(
    name = "USER_INFO"
)

class PuntoAquaApplication : Application() {

    lateinit var container: AppDataContainer
    lateinit var localRepository: LocalRepository

    override fun onCreate() {
        super.onCreate()
        localRepository = LocalRepository(userInfo)
        container = AppDataContainer()
    }
    
}