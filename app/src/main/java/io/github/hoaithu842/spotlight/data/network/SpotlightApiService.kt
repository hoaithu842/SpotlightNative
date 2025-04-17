package io.github.hoaithu842.spotlight.data.network

import io.github.hoaithu842.spotlight.data.network.dto.ArtistDetailsDto
import io.github.hoaithu842.spotlight.data.network.dto.HomeSectionDto
import io.github.hoaithu842.spotlight.data.network.dto.LibraryContentsDto
import io.github.hoaithu842.spotlight.data.network.dto.SuccessBodyDto
import io.github.hoaithu842.spotlight.data.network.dto.UserProfileDto
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotlightApiService {
    @GET("auth/session-user")
    suspend fun getSessionUser(): ApiResponse<SuccessBodyDto<UserProfileDto>>

    @GET("home")
    suspend fun getHomeContents(): ApiResponse<SuccessBodyDto<List<HomeSectionDto>?>>

    @GET("artists/{id}")
    suspend fun getArtist(
        @Path("id") id: String,
    ): ApiResponse<SuccessBodyDto<ArtistDetailsDto>>

    @GET("library")
    suspend fun getPlaylists(): ApiResponse<SuccessBodyDto<LibraryContentsDto>>
}
