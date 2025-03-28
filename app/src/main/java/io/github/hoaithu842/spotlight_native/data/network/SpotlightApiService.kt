package io.github.hoaithu842.spotlight_native.data.network

import io.github.hoaithu842.spotlight_native.data.network.dto.SuccessBodyDto
import io.github.hoaithu842.spotlight_native.data.network.dto.UserProfileDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import retrofit2.http.GET

interface SpotlightApiService {
    @GET("auth/session-user")
    suspend fun getSessionUser(): ApiResponse<SuccessBodyDto<UserProfileDto>>
}