package io.github.hoaithu842.spotlight.data.network.dto

import com.google.gson.annotations.SerializedName

data class SearchResultDto(
    @SerializedName("top_result")
    val topResult: TopResult?,
    @SerializedName("songs")
    val songs: List<Song>?,
    @SerializedName("artists")
    val artists: List<ArtistDto>?,
    @SerializedName("playlists")
    val playlists: List<Playlist>?,
) {
    data class TopResult(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("image")
        val image: ImageDto?,
    )

    data class Song(
        @SerializedName("id")
        val id: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("image")
        val image: ImageDto?,
    )

    data class Playlist(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("image")
        val image: ImageDto?,
    )
}
