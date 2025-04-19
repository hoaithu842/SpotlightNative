package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class AlbumCategoryItemDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("image")
    val image: ImageDto?,
    @SerializedName("song")
    val song: SongDto?,
)
