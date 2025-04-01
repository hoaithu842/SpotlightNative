package io.github.hoaithu842.spotlight_native.data.repository

import io.github.hoaithu842.spotlight_native.domain.model.SongDetails
import io.github.hoaithu842.spotlight_native.domain.repository.PlayerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepositoryImpl @Inject constructor() : PlayerRepository {
    override val songsList: List<SongDetails> = listOf(
        SongDetails(
            title = "Beautiful In White",
            artists = "Shayne Ward",
            source = "https://thantrieu.com/resources/music/1073419268.mp3",
            image = "https://thantrieu.com/resources/arts/1073419268.webp",
        ),
        SongDetails(
            title = "Giả Vờ Nhưng Em Yêu Anh",
            artists = "Miu Lê",
            source = "https://thantrieu.com/resources/music/1074592745.mp3",
            image = "https://thantrieu.com/resources/arts/1074183664.webp",
        ),
        SongDetails(
            title = "Thằng Điên",
            artists = "JustaTee ft Phương Ly",
            source = "https://thantrieu.com/resources/music/1078245010.mp3",
            image = "https://thantrieu.com/resources/arts/1078245010.webp",
        ),
        SongDetails(
            title = "Chạm Khẽ Tim Anh Một Chút Thôi",
            artists = "Noo Phước Thịnh",
            source = "https://thantrieu.com/resources/music/1078245023.mp3",
            image = "https://thantrieu.com/resources/arts/1078245023.webp",
        ),
        SongDetails(
            title = "Người Ấy",
            artists = "Trịnh Thăng Bình",
            source = "https://thantrieu.com/resources/music/1078245024.mp3",
            image = "https://thantrieu.com/resources/arts/1078245024.webp",
        ),
    )
}