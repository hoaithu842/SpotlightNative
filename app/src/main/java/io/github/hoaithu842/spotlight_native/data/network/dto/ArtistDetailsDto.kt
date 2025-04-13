package io.github.hoaithu842.spotlight_native.data.network.dto

import com.google.gson.annotations.SerializedName

data class ArtistDetailsDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("in_library")
    val inLibrary: Boolean?,
    @SerializedName("image")
    val image: ImageDto?,
    @SerializedName("categories")
    val categories: List<ArtistCategoryDto>?,
)
