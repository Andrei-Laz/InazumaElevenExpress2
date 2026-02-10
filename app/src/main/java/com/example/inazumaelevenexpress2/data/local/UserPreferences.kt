package com.example.inazumaelevenexpress2.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USERNAME_KEY = stringPreferencesKey("username")
    }

    suspend fun saveToken(token: String, username: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[USERNAME_KEY] = username
        }
    }

    fun getUsername(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USERNAME_KEY]
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
            preferences.remove(USERNAME_KEY)
        }
    }
}