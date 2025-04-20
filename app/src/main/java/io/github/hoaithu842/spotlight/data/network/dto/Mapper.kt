package io.github.hoaithu842.spotlight.data.network.dto

import android.util.Log
import io.github.hoaithu842.spotlight.domain.model.AlbumCategory
import io.github.hoaithu842.spotlight.domain.model.AlbumDetails
import io.github.hoaithu842.spotlight.domain.model.Artist
import io.github.hoaithu842.spotlight.domain.model.ArtistCategory
import io.github.hoaithu842.spotlight.domain.model.ArtistCategoryItem
import io.github.hoaithu842.spotlight.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight.domain.model.ArtistSong
import io.github.hoaithu842.spotlight.domain.model.ArtistSongs
import io.github.hoaithu842.spotlight.domain.model.Creator
import io.github.hoaithu842.spotlight.domain.model.HomeSection
import io.github.hoaithu842.spotlight.domain.model.HomeSectionItem
import io.github.hoaithu842.spotlight.domain.model.Image
import io.github.hoaithu842.spotlight.domain.model.LibraryContents
import io.github.hoaithu842.spotlight.domain.model.LibraryItem
import io.github.hoaithu842.spotlight.domain.model.Playlist
import io.github.hoaithu842.spotlight.domain.model.PlaylistSearchResult
import io.github.hoaithu842.spotlight.domain.model.RecommendedPlaylists
import io.github.hoaithu842.spotlight.domain.model.SearchResult
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.domain.model.SongInfoAlbum
import io.github.hoaithu842.spotlight.domain.model.SongSearchResult
import io.github.hoaithu842.spotlight.domain.model.TopSearchResult
import io.github.hoaithu842.spotlight.domain.model.UserProfile

fun UserProfileDto?.toDomain(): UserProfile =
    UserProfile(
        name = this?.name,
        pictureURL = this?.avatar?.url ?: "",
        id = this?.id,
        nickname = this?.username,
        email = this?.email,
        isEmailVerified = this?.emailVerified,
        familyName = null,
        createdAt = this?.createdAt,
    )

fun ImageDto?.toDomain(): Image =
    Image(
        id = this?.id ?: "",
        name = this?.name ?: "",
        url = this?.url ?: "",
        // TODO: may replace with an error placeholder
    )

fun ArtistCategoryItemDto?.toDomain(): ArtistCategoryItem =
    ArtistCategoryItem(
        id = this?.id ?: "",
        title = this?.title ?: "",
        type = this?.type ?: "",
        image = this?.image.toDomain(),
    )

fun ArtistCategoryDto?.toDomain(): ArtistCategory =
    ArtistCategory(
        id = this?.id ?: "",
        name = this?.name ?: "",
        items = this?.items?.map { it.toDomain() } ?: listOf(),
    )

fun ArtistDetailsDto?.toDomain(): ArtistDetails =
    ArtistDetails(
        id = this?.id ?: "",
        name = this?.name ?: "",
        inLibrary = this?.inLibrary ?: false,
        image = this?.image.toDomain(),
        categories = this?.categories?.map { it.toDomain() } ?: listOf(),
    )

fun SongDto.toDomain(): Song =
    Song(
        id = this.id ?: "",
        name = this.name ?: "",
        url = this.url ?: "",
        // TODO: replace with something
    )

fun ArtistDto.toDomain(): Artist =
    Artist(
        id = this.id ?: "",
        name = this.name ?: "",
    )

fun HomeSectionItemDto.toDomain(): HomeSectionItem =
    HomeSectionItem(
        id = this.id ?: "",
        title = this.title ?: "",
        name = this.name ?: "",
        type = this.type ?: "",
        inLibrary = this.inLibrary ?: false,
        image = this.image.toDomain(),
        song = this.song?.toDomain(),
        artists = this.artists?.map { it.toDomain() },
    )

fun HomeSectionDto.toDomain(): HomeSection =
    HomeSection(
        id = this.id ?: "",
        name = this.name ?: "",
        items = this.items?.map { it.toDomain() } ?: listOf(),
    )

fun CreatorDto?.toDomain(): Creator =
    Creator(
        id = this?.id ?: "",
        name = this?.name ?: "",
    )

fun LibraryItemDto.toDomain(): LibraryItem =
    LibraryItem(
        id = this.id ?: "",
        name = this.name ?: "",
        description = this.description ?: "",
        image = this.image.toDomain(),
        creator = this.creator.toDomain(),
        color = this.color ?: "#ffffff",
        isPublic = this.isPublic ?: false,
        inLibrary = this.inLibrary ?: false,
        createdAt = this.createdAt ?: "",
    )

fun LibraryContentsDto.toDomain(): LibraryContents =
    LibraryContents(
        items =
            this.items?.map {
                it.toDomain().also {
                    Log.d("Rachel", it.name)
                }
            } ?: listOf(),
    )

fun PlaylistDto?.toDomain(): Playlist =
    Playlist(
        id = this?.id,
        name = this?.name,
        description = this?.description,
        color = this?.color,
        createdAt = this?.createdAt,
    )

fun PlaylistsPagingDto?.toDomain(): RecommendedPlaylists =
    RecommendedPlaylists(
        items = this?.items?.map { it.toDomain() },
    )

fun AlbumCategoryItemDto.toDomain(): SongDetails =
    SongDetails(
        id = this.id ?: "",
        title = this.title ?: "",
        image = this.image.toDomain(),
        song = this.song?.toDomain(),
    )

fun AlbumDetailsDto?.toDomain(): AlbumDetails =
    AlbumDetails(
        id = this?.id,
        title = this?.title,
        color = this?.color,
        image = this?.image.toDomain(),
        isPublic = this?.isPublic,
        isOwned = this?.isOwned,
        inLibrary = this?.inLibrary,
        artists = this?.artist?.map { it.name }?.joinToString(separator = ", "),
        items =
            if (this != null && !this.categories.isNullOrEmpty()) {
                this.categories[0].items?.map { it.toDomain() } ?: listOf()
            } else {
                listOf()
            },
    )

fun ArtistSongDto.toDomain(): ArtistSong =
    ArtistSong(
        id = this.id ?: "",
        title = this.title ?: "",
        color = this.color ?: "",
        image = this.image.toDomain(),
        url = this.url ?: "",
        releaseDate = this.releaseDate ?: "",
        duration = this.duration ?: 0,
    )

fun ArtistSongsPagingDto?.toDomain(): ArtistSongs =
    ArtistSongs(
        items = this?.items?.map { it.toDomain() } ?: listOf(),
    )

fun SearchResultDto.TopResult.toDomain() =
    TopSearchResult(
        id = this.id,
        name = this.name,
        type = this.type,
        image = this.image.toDomain(),
    )

fun SearchResultDto.Song.toDomain(): SongSearchResult =
    SongSearchResult(
        id = this.id,
        title = this.title,
        image = this.image.toDomain(),
    )

fun SearchResultDto.Playlist.toDomain(): PlaylistSearchResult =
    PlaylistSearchResult(
        id = this.id,
        name = this.name,
        image = this.image.toDomain(),
    )

fun SearchResultDto?.toDomain(): SearchResult =
    SearchResult(
        topResult = this?.topResult?.toDomain(),
        songs = this?.songs?.map { it.toDomain() },
        artists = this?.artists?.map { it.toDomain() },
        playlists = this?.playlists?.map { it.toDomain() },
    )

fun AlbumCategoryDto.toDomain(): AlbumCategory =
    AlbumCategory(
        id = this.id,
        name = this.name,
        items = this.items?.map { it.toDomain() } ?: listOf(),
    )

fun SongInfoDto.SongInfoAlbum.toDomain(): SongInfoAlbum =
    SongInfoAlbum(
        id = this.id,
        title = this.title,
    )

fun SongInfoDto?.toDomain(): SongInfo =
    SongInfo(
        id = this?.id,
        title = this?.title,
        album = this?.album?.toDomain(),
        duration = this?.duration ?: 0,
        releaseDate = this?.releaseDate,
        color = this?.color,
        liked = this?.liked,
        url = this?.url,
        image = this?.image.toDomain(),
        categories = this?.categories?.map { it.toDomain() } ?: listOf(),
        artists = this?.artists?.map { it.toDomain() } ?: listOf(),
    )
