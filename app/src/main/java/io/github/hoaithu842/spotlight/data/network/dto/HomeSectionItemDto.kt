package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class HomeSectionItemDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("artists")
    val artists: List<ArtistDto>?,
    @SerializedName("image")
    val image: ImageDto?,
    @SerializedName("song")
    val song: SongDto?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("in_library")
    val inLibrary: Boolean?,
)
