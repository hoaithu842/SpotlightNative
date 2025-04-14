package io.github.hoaithu842.spotlight.domain.repository

import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import io.github.hoaithu842.spotlight.domain.model.UserProfile

interface UserRepository {
    suspend fun getSessionUser(): ApiResponse<UserProfile>
}
