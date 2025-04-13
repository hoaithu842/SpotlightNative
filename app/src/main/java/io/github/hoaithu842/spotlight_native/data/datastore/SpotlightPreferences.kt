package io.github.hoaithu842.spotlight_native.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SpotlightPreferences
    @Inject
    constructor(
        private val userPreferences: DataStore<Preferences>,
    ) {
        private object PreferencesKeys {
            val loggedIn = booleanPreferencesKey("logged_in")
            val accessToken = stringPreferencesKey("access_token")
            val expiresAt = stringPreferencesKey("expires_at")
        }

        val loggedInFlow: Flow<Boolean> =
            userPreferences.data.map { preferences ->
                preferences[PreferencesKeys.loggedIn] ?: true
            }

        val accessTokenFlow: Flow<String> =
            userPreferences.data.map { preferences ->
                preferences[PreferencesKeys.accessToken] ?: ""
            }

        val expiresAtFlow: Flow<String?> =
            userPreferences.data.map { preferences ->
                preferences[PreferencesKeys.expiresAt]
            }

        suspend fun setLoggedIn(status: Boolean) {
            try {
                userPreferences.edit { preferences ->
                    preferences[PreferencesKeys.loggedIn] = status
                }
            } catch (ioException: IOException) {
                Log.e("Error", "Failed to update user preferences", ioException)
            }
        }

        suspend fun setAccessToken(accessToken: String) {
            try {
                userPreferences.edit { preferences ->
                    preferences[PreferencesKeys.accessToken] = accessToken
                }
            } catch (ioException: IOException) {
                Log.e("Error", "Failed to update user preferences", ioException)
            }
        }

        suspend fun setExpiresAt(expiresAt: String) {
            try {
                userPreferences.edit { preferences ->
                    preferences[PreferencesKeys.expiresAt] = expiresAt
                }
            } catch (ioException: IOException) {
                Log.e("Error", "Failed to update user preferences", ioException)
            }
        }
    }
