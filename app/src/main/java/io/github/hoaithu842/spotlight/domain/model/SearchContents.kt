package io.github.hoaithu842.spotlight.domain.model

data class RecommendedPlaylists(
    val items: List<Playlist>? = listOf(),
)

data class Playlist(
    val id: String? = "",
    val name: String? = "",
    val description: String? = "",
    val color: String? = "",
    val createdAt: String? = "",
)