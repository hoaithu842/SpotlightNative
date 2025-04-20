package io.github.hoaithu842.spotlight.domain.model

data class SongInfo(
    val id: String?,
    val title: String?,
    val album: SongInfoAlbum?,
    val duration: Long,
    val releaseDate: String?,
    val color: String?,
    val url: String?,
    val image: Image?,
    val categories: List<AlbumCategory>?,
    val artists: List<Artist>?,
)

data class SongInfoAlbum(
    val id: String?,
    val title: String?,
)

data class AlbumCategory(
    val id: String?,
    val name: String?,
    val items: List<SongDetails>?,
)
