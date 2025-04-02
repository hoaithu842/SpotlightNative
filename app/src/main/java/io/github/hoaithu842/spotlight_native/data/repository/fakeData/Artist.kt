package io.github.hoaithu842.spotlight_native.data.repository.fakeData

import io.github.hoaithu842.spotlight_native.data.network.dto.ArtistCategoryDto
import io.github.hoaithu842.spotlight_native.data.network.dto.ArtistCategoryItemDto
import io.github.hoaithu842.spotlight_native.data.network.dto.ArtistDetailsDto
import io.github.hoaithu842.spotlight_native.data.network.dto.ImageDto

val fakeArtistDetails = ArtistDetailsDto(
    id = "672ad193d1722d85eac0e19d",
    name = "Mason Nguyen",
    inLibrary = false,
    image = ImageDto(
        id = "672ad1920007043a7df25091",
        name = "images\\Mason Nguyen.jpg",
        url = "https://p.vnmusic.org/media/api/v1/upload/672ad1920007043a7df25091"
    ),
    categories = listOf(
        ArtistCategoryDto(
            id = "671f4b2c3ef260b78c4206b5",
            name = "Artist Playlists",
            items = listOf(
                ArtistCategoryItemDto(
                    id = "672b2a4247b8bd766d88ca0d",
                    title = "YanBi",
                    type = "artist",
                    image = ImageDto(
                        id = "672b2a420007043a7df26592",
                        name = "images\\YanBi.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/672b2a420007043a7df26592"
                    )
                ),
                ArtistCategoryItemDto(
                    id = "672ad249d1722d85eac0f149",
                    title = "BMZ",
                    type = "artist",
                    image = ImageDto(
                        id = "672ad2490007043a7df26040",
                        name = "images\\BMZ.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/672ad2490007043a7df26040"
                    )
                )
            )
        ),
        ArtistCategoryDto(
            id = "671f4b2c3ef260b78c4206b3",
            name = "Featuring Artist Name",
            items = listOf(
                ArtistCategoryItemDto(
                    id = "672ad19ad1722d85eac0e293",
                    title = "Quân Lee",
                    type = "artist",
                    image = ImageDto(
                        id = "672ad19a0007043a7df25187",
                        name = "images\\Quân Lee.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/672ad19a0007043a7df25187"
                    )
                ),
                ArtistCategoryItemDto(
                    id = "6734ea2ec17154a25ac69afb",
                    title = "Hao Nguyen",
                    type = "artist",
                    image = ImageDto(
                        id = "6734ea2ce7f6510f82388dd8",
                        name = "2024-10-15 21.51.56.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/6734ea2ce7f6510f82388dd8"
                    )
                )
            )
        ),
        ArtistCategoryDto(
            id = "671f4b2c3ef260b78c4206b4",
            name = "Fans also like",
            items = listOf(
                ArtistCategoryItemDto(
                    id = "672ad198d1722d85eac0e239",
                    title = "Phuc Du",
                    type = "artist",
                    image = ImageDto(
                        id = "672ad1970007043a7df2512d",
                        name = "images\\Phuc Du.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/672ad1970007043a7df2512d"
                    )
                ),
                ArtistCategoryItemDto(
                    id = "672ad18dd1722d85eac0e0e1",
                    title = "Karik",
                    type = "artist",
                    image = ImageDto(
                        id = "672ad18d0007043a7df24fd6",
                        name = "images\\Karik.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/672ad18d0007043a7df24fd6"
                    )
                ),
                ArtistCategoryItemDto(
                    id = "672ad19ad1722d85eac0e293",
                    title = "Quân Lee",
                    type = "artist",
                    image = ImageDto(
                        id = "672ad18d0007043a7df24fd6",
                        name = "images\\Karik.jpg",
                        url = "https://p.vnmusic.org/media/api/v1/upload/672ad19a0007043a7df25187"
                    )
                ),
            )
        )
    )
)