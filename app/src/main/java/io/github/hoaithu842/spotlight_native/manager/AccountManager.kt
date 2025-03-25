package io.github.hoaithu842.spotlight_native.manager

import io.github.hoaithu842.spotlight_native.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface AccountManager {
    val isLoggedIn: Flow<Boolean>
    val currentUserProfile: Flow<UserProfile?>
    fun reloadCredentials()
}