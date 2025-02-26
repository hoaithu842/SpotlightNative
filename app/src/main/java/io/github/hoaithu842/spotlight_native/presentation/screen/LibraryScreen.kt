package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.R
import io.github.hoaithu842.spotlight_native.extension.singleClickable
import io.github.hoaithu842.spotlight_native.presentation.component.LibraryArtistItem
import io.github.hoaithu842.spotlight_native.presentation.component.LibraryPlaylistItem
import io.github.hoaithu842.spotlight_native.ui.designsystem.LibraryTopAppBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle

@Composable
fun LibraryScreen(
    onAvatarClick: () -> Unit,
    onNavigateToSearchClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        var isInGridView by remember { mutableStateOf(true) }

        LibraryTopAppBar(
            onAvatarClick = onAvatarClick,
            onNavigateToSearchClick = onNavigateToSearchClick,
            modifier = Modifier
                .statusBarsPadding()
                .height(SpotlightDimens.TopAppBarHeight * 2)
        )

        SortAndViewBar(
            isInGridView = isInGridView,
            onChangeViewClick = { isInGridView = it },
            modifier = Modifier.padding(
                horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding * 2,
                vertical = SpotlightDimens.TopAppBarIconHorizontalPadding,
            ),
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            columns = if (isInGridView) GridCells.Adaptive(100.dp) else GridCells.Fixed(1),
        ) {
            item {
                LibraryArtistItem(
                    artist = "Justatee",
                    imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                    modifier = Modifier.padding(SpotlightDimens.TopAppBarHorizontalPadding * 2),
                    isInGridView = isInGridView,
                )
            }

            item {
                LibraryArtistItem(
                    artist = "Noo Phuoc Thinh",
                    imageUrl = "https://thantrieu.com/resources/arts/1078245023.webp",
                    modifier = Modifier.padding(SpotlightDimens.TopAppBarHorizontalPadding * 2),
                    isInGridView = isInGridView,
                )
            }

            item {
                LibraryPlaylistItem(
                    creator = "User",
                    imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                    modifier = Modifier.padding(SpotlightDimens.TopAppBarHorizontalPadding * 2),
                    isInGridView = isInGridView,
                )
            }

            item {
                LibraryArtistItem(
                    artist = "Justatee",
                    imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                    modifier = Modifier.padding(SpotlightDimens.TopAppBarHorizontalPadding * 2),
                    isInGridView = isInGridView,
                )
            }

            items(50) {
                LibraryArtistItem(
                    artist = "Justatee",
                    imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                    modifier = Modifier.padding(SpotlightDimens.TopAppBarHorizontalPadding * 2),
                    isInGridView = isInGridView,
                )
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SpotlightDimens.MinimizedPlayerHeight)
                )
            }
        }
    }
}


@Composable
fun SortAndViewBar(
    onChangeViewClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isInGridView: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.TopAppBarOptionHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(SpotlightIcons.Filter),
                contentDescription = "",
                modifier = Modifier.size(SpotlightDimens.LibraryFunctionBarIconSize),
                tint = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(R.string.recents),
                style = SpotlightTextStyle.Text11W600,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding)
            )
        }
        Icon(
            painter = painterResource(if (!isInGridView) SpotlightIcons.GridView else SpotlightIcons.ListView),
            contentDescription = "",
            modifier = Modifier
                .size(SpotlightDimens.LibraryFunctionBarIconSize)
                .singleClickable { onChangeViewClick(!isInGridView) },
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}