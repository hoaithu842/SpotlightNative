package io.github.hoaithu842.spotlight.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.github.hoaithu842.spotlight.domain.model.Image
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight.ui.theme.SpotlightTheme

@Composable
fun SongItem(
    song: Song,
    cover: Image,
    modifier: Modifier = Modifier,
    artists: String = "Song",
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.FullsizePlayerTopAppBarHeight),
    ) {
        Cover(
            imageUrl = cover.url,
            modifier =
                Modifier
                    .size(SpotlightDimens.FullsizePlayerTopAppBarHeight)
                    .align(Alignment.CenterStart),
        )

        Column(
            modifier =
                Modifier
                    .padding(horizontal = SpotlightDimens.FullsizePlayerTopAppBarHeight)
                    .fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.LibraryTextPadding)
                        .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = song.name,
                    style = SpotlightTextStyle.Text16W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = artists.ifBlank { "Song" },
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    modifier = Modifier.padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f)),
                )
            }
        }
        Box(
            modifier =
                Modifier
                    .size(SpotlightDimens.FullsizePlayerTopAppBarHeight)
                    .align(Alignment.CenterEnd),
        ) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = "",
                modifier =
                    Modifier
                        .size(SpotlightDimens.SongItemIconSize)
                        .align(Alignment.Center),
            )
        }
    }
}

@Preview
@Composable
fun TrackPreview() {
    SpotlightTheme {
        SongItem(Song(), Image())
    }
}
