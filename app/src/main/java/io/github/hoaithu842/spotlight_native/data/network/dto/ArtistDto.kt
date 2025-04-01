package io.github.hoaithu842.spotlight_native.data.network.dto

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,
)