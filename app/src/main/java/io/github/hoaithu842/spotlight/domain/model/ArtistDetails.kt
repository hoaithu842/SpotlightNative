package io.github.hoaithu842.spotlight.domain.model

data class ArtistDetails(
    val id: String = "",
    val name: String = "",
    val inLibrary: Boolean = false,
    val image: Image?,
    val categories: List<ArtistCategory> = listOf(),
)

data class ArtistCategory(
    val id: String = "",
    val name: String = "",
    val items: List<ArtistCategoryItem> = listOf(),
)

data class ArtistCategoryItem(
    val id: String = "",
    val title: String = "",
    val type: String = "",
    val image: Image,
)
