package io.github.hoaithu842.spotlight.domain.model

data class AlbumDetails(
    val id: String? = "",
    val title: String? = "",
    val color: String? = "",
    val image: Image? = Image(),
    val isPublic: Boolean? = false,
    val isOwned: Boolean? = false,
    val inLibrary: Boolean? = false,
    val artists: String? = "",
    val items: List<SongDetails>? = listOf(),
)
