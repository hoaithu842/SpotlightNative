package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class ArtistSongDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("image")
    val image: ImageDto?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("duration")
    val duration: Int?,
)
