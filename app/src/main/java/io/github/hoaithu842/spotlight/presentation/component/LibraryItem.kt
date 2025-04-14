package io.github.hoaithu842.spotlight.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            modifier = modifier.padding(vertical = 5.dp),
        )
    } else {
        VerticalCircularWithTitleThumbnail(
            imageUrl = imageUrl,
            title = artist,
            description = "abc",
            onClick = {},
            modifier = modifier,
            thumbnailCoverSize = ThumbnailCoverSize.MEDIUM,
        )
    }
}

@Composable
fun LibraryPlaylistItem(
    name: String,
    type: String,
    creator: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    isInGridView: Boolean = true,
) {
    if (!isInGridView) {
        HorizontalWithTitleThumbnail(
            imageUrl = imageUrl,
            title = name,
            description = "$type • $creator",
            onClick = {},
            modifier = modifier.padding(vertical = 5.dp),
        )
    } else {
        VerticalWithTitleThumbnail(
            imageUrl = imageUrl,
            title = name,
            description = "$type • $creator",
            onClick = {},
            modifier = modifier,
            thumbnailCoverSize = ThumbnailCoverSize.MEDIUM,
        )
    }
}
