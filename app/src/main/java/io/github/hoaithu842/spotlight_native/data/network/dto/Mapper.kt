package io.github.hoaithu842.spotlight_native.data.network.dto

import android.util.Log
import io.github.hoaithu842.spotlight_native.domain.model.Artist
import io.github.hoaithu842.spotlight_native.domain.model.ArtistCategory
import io.github.hoaithu842.spotlight_native.domain.model.ArtistCategoryItem
import io.github.hoaithu842.spotlight_native.domain.model.ArtistDetails
import io.github.hoaithu842.spotlight_native.domain.model.Creator
import io.github.hoaithu842.spotlight_native.domain.model.HomeSection
import io.github.hoaithu842.spotlight_native.domain.model.HomeSectionItem
import io.github.hoaithu842.spotlight_native.domain.model.Image
import io.github.hoaithu842.spotlight_native.domain.model.LibraryContents
import io.github.hoaithu842.spotlight_native.domain.model.LibraryItem
import io.github.hoaithu842.spotlight_native.domain.model.Song
import io.github.hoaithu842.spotlight_native.domain.model.UserProfile

fun UserProfileDto.toDomain(): UserProfile =
    UserProfile(
        name = this.name,
        pictureURL = this.picture,
        id = null,
        nickname = null,
        email = null,
        isEmailVerified = null,
        familyName = null,
        createdAt = null,
    )

fun ImageDto?.toDomain(): Image =
    Image(
        id = this?.id ?: "",
        name = this?.name ?: "",
        url = this?.url ?: "", // TODO: may replace with an error placeholder
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

fun ArtistDetailsDto.toDomain(): ArtistDetails =
    ArtistDetails(
        id = this.id ?: "",
        name = this.name ?: "",
        inLibrary = this.inLibrary ?: false,
        image = this.image.toDomain(),
        categories = this.categories?.map { it.toDomain() } ?: listOf(),
    )

fun SongDto.toDomain(): Song =
    Song(
        id = this.id ?: "",
        name = this.name ?: "",
        url = this.url ?: "", // TODO: replace with something
    )

fun ArtistDto.toDomain(): Artist =
    Artist(
        id = this.id ?: "",
        name = this.name ?: "",
    )

fun HomeSectionItemDto.toDomain(): HomeSectionItem =
    HomeSectionItem(
        id = this.id ?: "",
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
