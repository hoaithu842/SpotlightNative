package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class SongInfoDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("album")
    val album: SongInfoAlbum?,
    @SerializedName("duration")
    val duration: Long?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("image")
    val image: ImageDto?,
    @SerializedName("categories")
    val categories: List<AlbumCategoryDto>?,
    @SerializedName("artists")
    val artists: List<ArtistDto>?,
) {
    data class SongInfoAlbum(
        @SerializedName("id")
        val id: String?,
        @SerializedName("title")
        val title: String?,
    )
}
