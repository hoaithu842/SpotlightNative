package io.github.hoaithu842.spotlight_native.domain.repository

import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import io.github.hoaithu842.spotlight_native.domain.model.UserProfile

interface UserRepository {
    suspend fun getSessionUser(): ApiResponse<UserProfile>
}