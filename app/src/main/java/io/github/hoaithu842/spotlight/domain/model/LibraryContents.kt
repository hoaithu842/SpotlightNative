package io.github.hoaithu842.spotlight.domain.model

data class LibraryContents(
    val items: List<LibraryItem> = listOf(),
)

data class LibraryItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val image: Image = Image(),
    val creator: Creator,
    val color: String = "#ffffff",
    val isPublic: Boolean = true,
    val inLibrary: Boolean = false,
    val createdAt: String = "",
)

data class Creator(
    val id: String = "",
    val name: String = "",
)
