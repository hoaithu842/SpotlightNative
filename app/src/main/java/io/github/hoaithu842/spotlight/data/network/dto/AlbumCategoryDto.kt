package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class AlbumCategoryDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("items")
    val items: List<AlbumCategoryItemDto>?,
)
