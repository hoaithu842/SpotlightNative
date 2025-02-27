package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LibraryArtistItem(
    artist: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    isInGridView: Boolean = true,
) {
    if (!isInGridView) {
        HorizontalCircularWithTitleThumbnail(
            imageUrl = imageUrl,
            title = artist,
            description = "abc",
            onClick = {},
            modifier = modifier,
        )
    } else {
        VerticalCircularWithTitleThumbnail(
            imageUrl = imageUrl,
            title = artist,
            description = "abc",
            onClick = {},
            modifier = modifier,
        )
    }
}

@Composable
fun LibraryPlaylistItem(
    creator: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    isInGridView: Boolean = true,
) {
    if (!isInGridView) {
        HorizontalWithTitleThumbnail(
            imageUrl = imageUrl,
            title = creator,
            description = "abc",
            onClick = {},
            modifier = modifier,
        )
    } else {
        VerticalWithTitleThumbnail(
            imageUrl = imageUrl,
            title = creator,
            description = "abc",
            onClick = {},
            modifier = modifier,
        )
    }
}