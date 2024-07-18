package com.example.puntoaqua.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LocalRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val TOKEN = stringPreferencesKey("token")
    }

    val token: Flow<String> = dataStore.data
        .catch {  }
        .map { preferences ->
            preferences[TOKEN].toString()
        }
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] ?: ""
            preferences[TOKEN] = token
        }
    }

}