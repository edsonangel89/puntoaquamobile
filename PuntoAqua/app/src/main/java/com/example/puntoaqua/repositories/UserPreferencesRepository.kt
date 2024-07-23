package com.example.puntoaqua.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class LocalRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val TOKEN = stringPreferencesKey("token")
        val IS_USER_LOGGED = booleanPreferencesKey("is_user_logged")
    }

    val token: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[TOKEN].toString()
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    val userState: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[IS_USER_LOGGED] ?: false
        }

    suspend fun saveLoginState(loginState: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED] = loginState
        }
    }

}