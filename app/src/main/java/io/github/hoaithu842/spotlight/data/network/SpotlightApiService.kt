package io.github.hoaithu842.spotlight.data.network

import io.github.hoaithu842.spotlight.data.network.dto.AlbumDetailsDto
import io.github.hoaithu842.spotlight.data.network.dto.ArtistDetailsDto
import io.github.hoaithu842.spotlight.data.network.dto.ArtistSongsPagingDto
import io.github.hoaithu842.spotlight.data.network.dto.HomeSectionDto
import io.github.hoaithu842.spotlight.data.network.dto.LibraryContentsDto
import io.github.hoaithu842.spotlight.data.network.dto.PlaylistsPagingDto
import io.github.hoaithu842.spotlight.data.network.dto.SearchResultDto
import io.github.hoaithu842.spotlight.data.network.dto.SongInfoDto
import io.github.hoaithu842.spotlight.data.network.dto.SuccessBodyDto
import io.github.hoaithu842.spotlight.data.network.dto.UserProfileDto
import io.github.hoaithu842.spotlight.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotlightApiService {
    @GET("auth/session-user")
    suspend fun getSessionUser(): ApiResponse<SuccessBodyDto<UserProfileDto>>

    @GET("home")
    suspend fun getHomeContents(): ApiResponse<SuccessBodyDto<List<HomeSectionDto>?>>

    @GET("playlists")
    suspend fun getPlaylists(): ApiResponse<SuccessBodyDto<PlaylistsPagingDto?>>

    @GET("albums/{id}")
    suspend fun getAlbum(
        @Path("id") id: String,
    ): ApiResponse<SuccessBodyDto<AlbumDetailsDto>>

    @GET("artists/{id}")
    suspend fun getArtist(
        @Path("id") id: String,
    ): ApiResponse<SuccessBodyDto<ArtistDetailsDto>>

    @GET("songs/{id}")
    suspend fun getSongInfo(
        @Path("id") id: String,
    ): ApiResponse<SuccessBodyDto<SongInfoDto>>

    @GET("songs")
    suspend fun getArtistSongs(
        @Query("artist_ids[]") artistId: String,
    ): ApiResponse<SuccessBodyDto<ArtistSongsPagingDto>>

    @GET("library")
    suspend fun getLibrary(): ApiResponse<SuccessBodyDto<LibraryContentsDto>>

    @GET("search")
    suspend fun getSearchResult(
        @Query("q") query: String,
    ): ApiResponse<SuccessBodyDto<SearchResultDto>>
}
