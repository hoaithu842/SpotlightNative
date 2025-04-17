package io.github.hoaithu842.spotlight.domain.model

data class HomeContents(
    val contents: List<HomeSection> = listOf(),
)

data class HomeSection(
    val id: String = "",
    val name: String = "",
    val items: List<HomeSectionItem> = listOf(),
)

data class HomeSectionItem(
    val id: String = "",
    val title: String = "",
    val type: String = "",
    val artists: List<Artist>?,
    val image: Image?,
    val song: Song?,
    val name: String = "",
    val inLibrary: Boolean = false,
)

data class Song(
    val id: String = "",
    val name: String = "",
    val url: String = "",
)

data class Artist(
    val id: String = "",
    val name: String = "",
)
