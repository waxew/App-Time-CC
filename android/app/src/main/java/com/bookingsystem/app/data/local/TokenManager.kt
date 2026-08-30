package com.bookingsystem.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "booking_prefs")

class TokenManager(private val context: Context) {
    private val tokenKey = stringPreferencesKey("auth_token")
    val tokenFlow: Flow<String?> = context.dataStore.data.map { prefs -> prefs[tokenKey] }
    suspend fun getToken(): String? = context.dataStore.data.map { it[tokenKey] }.first()
    suspend fun saveToken(token: String) { context.dataStore.edit { prefs -> prefs[tokenKey] = token } }
    suspend fun clearToken() { context.dataStore.edit { prefs -> prefs.remove(tokenKey) } }
}
