package io.github.hoaithu842.spotlight.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getLoggedIn(): Flow<Boolean>

    suspend fun setLoggedIn(status: Boolean)

    fun getAccessToken(): Flow<String>

    suspend fun setAccessToken(accessToken: String)

    fun getExpiresAt(): Flow<String?>

    suspend fun setExpiresAt(expiresAt: String)
}
