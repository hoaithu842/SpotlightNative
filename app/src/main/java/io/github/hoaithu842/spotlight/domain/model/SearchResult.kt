package io.github.hoaithu842.spotlight.domain.model

data class SearchResult(
    val topResult: TopSearchResult?,
    val songs: List<SongSearchResult>?,
    val artists: List<Artist>?,
    val playlists: List<PlaylistSearchResult>?,
)

data class TopSearchResult(
    val id: String?,
    val name: String?,
    val type: String?,
    val image: Image,
)

data class SongSearchResult(
    val id: String?,
    val title: String?,
    val image: Image,
)

data class PlaylistSearchResult(
    val id: String?,
    val name: String?,
    val image: Image,
)
