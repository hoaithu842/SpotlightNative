package io.github.hoaithu842.spotlight_native.domain.repository

import io.github.hoaithu842.spotlight_native.data.network.dto.SuccessBodyDto
import io.github.hoaithu842.spotlight_native.data.network.dto.UserProfileDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse

interface UserRepository {
    suspend fun getSessionUser(): ApiResponse<SuccessBodyDto<UserProfileDto>>
}