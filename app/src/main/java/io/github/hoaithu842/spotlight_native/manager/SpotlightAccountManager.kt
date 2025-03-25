package io.github.hoaithu842.spotlight_native.manager

import android.util.Log
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.model.UserProfile
import io.github.hoaithu842.spotlight_native.domain.repository.PreferencesRepository
import io.github.hoaithu842.spotlight_native.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


class SpotlightAccountManager @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
) : AccountManager {
    override val isLoggedIn: Flow<Boolean> = preferencesRepository.getLoggedIn()

    private val _currentUserProfile: MutableStateFlow<UserProfile?> = MutableStateFlow(null)
    override val currentUserProfile: StateFlow<UserProfile?> = _currentUserProfile.asStateFlow()

    override fun reloadCredentials() {
        val coroutineScope = CoroutineScope(Job())
        coroutineScope.launch {
            if (isLoggedIn.first()) {
                val expiresAt = preferencesRepository.getExpiresAt().first()
                val formatter =
                    DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH)
                if (expiresAt != null) {
                    val expirationDate = ZonedDateTime.parse(expiresAt, formatter)
                    val now = ZonedDateTime.now()
                    if (now.isAfter(expirationDate)) {
                        Log.d("Rachel", "Session expired")
                    } else {
                        Log.d("Rachel", "Session is still valid")
                        val response = userRepository.getSessionUser()
                        when (response) {
                            is ApiResponse.Error -> Log.d(
                                "Rachel",
                                ("Error: " + response.message) ?: ""
                            )

                            is ApiResponse.Exception -> Log.d(
                                "Rachel",
                                ("Exception: " + response.e.message) ?: ""
                            )

                            is ApiResponse.Success -> Log.d(
                                "Rachel",
                                "Success" + response.data.toString()
                            )
                        }
                    }
                }
            }
        }
    }
}