package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class PlaylistDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("color")
    val color: String?,
    @SerializedName("is_public")
    val isPublic: Boolean?,
    @SerializedName("in_library")
    val inLibrary: Boolean?,
    @SerializedName("created_at")
    val createdAt: String?,
)
