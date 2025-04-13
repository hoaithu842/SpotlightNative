package io.github.hoaithu842.spotlight_native.data.network

import io.github.hoaithu842.spotlight_native.data.network.dto.ArtistDetailsDto
import io.github.hoaithu842.spotlight_native.data.network.dto.HomeContentsDto
import io.github.hoaithu842.spotlight_native.data.network.dto.LibraryContentsDto
import io.github.hoaithu842.spotlight_native.data.network.dto.SuccessBodyDto
import io.github.hoaithu842.spotlight_native.data.network.dto.UserProfileDto
import io.github.hoaithu842.spotlight_native.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotlightApiService {
    @GET("auth/session-user")
    suspend fun getSessionUser(): ApiResponse<SuccessBodyDto<UserProfileDto>>

    @GET("home")
    suspend fun getHomeContents(): ApiResponse<SuccessBodyDto<HomeContentsDto>>

    @GET("artists/{id}")
    suspend fun getArtist(
        @Path("id") id: String,
    ): ApiResponse<SuccessBodyDto<ArtistDetailsDto>>

    @GET("playlists")
    suspend fun getPlaylists(): ApiResponse<SuccessBodyDto<LibraryContentsDto>>
}
