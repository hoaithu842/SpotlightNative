package io.github.hoaithu842.spotlight.domain.model

data class SongDetails(
    val id: String? = "",
    val title: String? = "",
    val image: Image? = Image(),
    val song: Song? = Song(),
)
