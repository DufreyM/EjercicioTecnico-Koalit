package com.example.koalit_recetas.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    val userEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[EMAIL_KEY]
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_LOGGED_IN] ?: false
    }

    val rememberMe: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[REMEMBER_ME] ?: false
    }

    suspend fun saveSession(email: String, rememberMe: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
            prefs[REMEMBER_ME] = rememberMe
            prefs[IS_LOGGED_IN] = rememberMe // 🔹 Solo marcar como logged in si rememberMe es true
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs.remove(EMAIL_KEY)
            prefs[IS_LOGGED_IN] = false
            prefs[REMEMBER_ME] = false
        }
    }

    // Toma en cuenta si se marco el rememberMe para mantener la sesión activa.
    suspend fun Staylogged(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[IS_LOGGED_IN] == true && prefs[REMEMBER_ME] == true
    }
}
