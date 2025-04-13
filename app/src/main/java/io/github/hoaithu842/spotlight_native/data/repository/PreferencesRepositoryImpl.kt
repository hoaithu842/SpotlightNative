package io.github.hoaithu842.spotlight_native.data.repository

import io.github.hoaithu842.spotlight_native.data.datastore.SpotlightPreferences
import io.github.hoaithu842.spotlight_native.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl
    @Inject
    constructor(
        private val datastoreDataSource: SpotlightPreferences,
    ) : PreferencesRepository {
        override fun getLoggedIn(): Flow<Boolean> {
            return datastoreDataSource.loggedInFlow
        }

        override suspend fun setLoggedIn(status: Boolean) {
            datastoreDataSource.setLoggedIn(status)
        }

        override fun getAccessToken(): Flow<String> {
            return datastoreDataSource.accessTokenFlow
        }

        override suspend fun setAccessToken(accessToken: String) {
            datastoreDataSource.setAccessToken(accessToken)
        }

        override fun getExpiresAt(): Flow<String?> {
            return datastoreDataSource.expiresAtFlow
        }

        override suspend fun setExpiresAt(expiresAt: String) {
            datastoreDataSource.setExpiresAt(expiresAt)
        }
    }
