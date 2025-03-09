package io.github.hoaithu842.spotlight_native.data.network.dto

import com.google.gson.annotations.SerializedName

data class SongDetailDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("in_library")
    val inLibrary: String,

    @SerializedName("image")
    val image: ImageDto,
) {
    data class ImageDto(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("url")
        val url: String,
    )
}