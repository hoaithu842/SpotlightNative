package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray

@Composable
fun LibraryArtistItem(
    artist: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    isInGridView: Boolean = true,
) {
    if (!isInGridView) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(SpotlightDimens.LibraryItemHeight)
        ) {
            ArtistThumbnail(
                imageUrl = imageUrl,
                modifier = Modifier.size(SpotlightDimens.LibraryItemHeight)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = SpotlightDimens.LibraryTextPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = artist,
                    style = SpotlightTextStyle.Text16W400,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "abc",
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    modifier = Modifier.padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f))
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .width(SpotlightDimens.LibraryItemListWidth)
                .height(SpotlightDimens.LibraryItemListMaxHeight),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //LibraryItemListHeight
            ArtistThumbnail(
                imageUrl = imageUrl,
                modifier = Modifier.size(SpotlightDimens.LibraryItemListThumbnailSize)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = artist,
                    style = SpotlightTextStyle.Text11W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "abc",
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    modifier = Modifier
                        .padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f))
                        .fillMaxWidth(),
                )
            }

        }
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
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(SpotlightDimens.LibraryItemHeight)
        ) {
            LibraryPlaylistThumbnail(
                imageUrl = imageUrl,
                modifier = Modifier.size(SpotlightDimens.LibraryItemHeight)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = SpotlightDimens.LibraryTextPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = creator,
                    style = SpotlightTextStyle.Text16W400,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "abc",
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    modifier = Modifier.padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f))
                )
            }
        }
    } else {
        Column(
            modifier = modifier
                .width(SpotlightDimens.LibraryItemListWidth)
                .height(SpotlightDimens.LibraryItemListMaxHeight),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //LibraryItemListHeight
            LibraryPlaylistThumbnail(
                imageUrl = imageUrl,
                modifier = Modifier.size(SpotlightDimens.LibraryItemListThumbnailSize)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "artist",
                    style = SpotlightTextStyle.Text11W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = "abc",
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    modifier = Modifier
                        .padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f))
                        .fillMaxWidth(),
                )
            }

        }
    }
}