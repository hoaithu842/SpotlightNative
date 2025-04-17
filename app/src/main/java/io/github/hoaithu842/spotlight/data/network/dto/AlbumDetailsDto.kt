package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class AlbumDetailsDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("image")
    val image: ImageDto?,
    @SerializedName("is_public")
    val isPublic: Boolean?,
    @SerializedName("is_owned")
    val isOwned: Boolean?,
    @SerializedName("in_library")
    val inLibrary: Boolean?,
    @SerializedName("categories")
    val categories: List<Category>?,
) {
    data class Category(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("items")
        val items: List<AlbumCategoryItem>?,
    )
}
