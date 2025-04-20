package io.github.hoaithu842.spotlight.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.hoaithu842.spotlight.domain.model.SearchResult
import io.github.hoaithu842.spotlight.domain.model.SongSearchResult
import io.github.hoaithu842.spotlight.presentation.component.HorizontalWithTitleThumbnail
import io.github.hoaithu842.spotlight.presentation.viewmodel.SearchResultUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.SearchResultViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.DotsCollision
import io.github.hoaithu842.spotlight.ui.designsystem.SearchResultTopAppBar
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle

@Composable
fun SearchResultScreen(
    onPlaylistClick: (String) -> Unit,
    onArtistClick: (String) -> Unit,
    onCancelClick: () -> Unit,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val uiState by viewModel.searchResultUiState.collectAsStateWithLifecycle()

    var searchQuery by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        SearchResultTopAppBar(
            searchQuery = searchQuery,
            onCancelClick = onCancelClick,
            onSearchQueryChanged = {
                searchQuery = it
                viewModel.onChangeSearchQuery(it)
            },
            modifier =
                Modifier
                    .statusBarsPadding()
                    .height(SpotlightDimens.TopAppBarHeight),
        )
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
        ) {
            when (uiState) {
                SearchResultUiState.Error -> {}
                SearchResultUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        DotsCollision()
                    }
                }

                is SearchResultUiState.Success -> {
                    val result = (uiState as SearchResultUiState.Success).result
                    ResultDisplay(
                        result = result,
                        onSongClick = {
                            viewModel.playSong(it)
                        },
                        onArtistClick = onArtistClick,
                        onPlaylistClick = onPlaylistClick,
                    )
                }
            }
        }
    }
}

@Composable
fun ResultDisplay(
    result: SearchResult,
    onSongClick: (SongSearchResult?) -> Unit,
    onPlaylistClick: (String) -> Unit,
    onArtistClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        item {
            Text(
                "Top Search Result",
                style = SpotlightTextStyle.Text16W600,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(10.dp),
            )
        }
        item {
            if (result.topResult != null) {
                HorizontalWithTitleThumbnail(
                    imageUrl = "",
                    title = result.topResult.name ?: "",
                    description = result.topResult.type ?: "",
                    onClick = {},
                    modifier = Modifier.padding(5.dp),
                )
            }
        }
        item {
            Text(
                "Songs",
                style = SpotlightTextStyle.Text16W600,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(10.dp),
            )
        }
        items(count = result.songs?.size ?: 0) {
            HorizontalWithTitleThumbnail(
                imageUrl = "",
                title = result.songs?.get(it)?.title ?: "",
                description = "Song",
                onClick = { onSongClick(result.songs?.get(it)) },
                modifier = Modifier.padding(5.dp),
            )
        }
        item {
            Text(
                "Artists",
                style = SpotlightTextStyle.Text16W600,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(10.dp),
            )
        }
        items(count = result.artists?.size ?: 0) {
            HorizontalWithTitleThumbnail(
                imageUrl = "",
                title = result.artists?.get(it)?.name ?: "",
                description = "Artist",
                onClick = { onArtistClick(result.artists?.get(it)?.id ?: "") },
                modifier = Modifier.padding(5.dp),
            )
        }
        item {
            Text(
                "Playlists",
                style = SpotlightTextStyle.Text16W600,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(10.dp),
            )
        }
        items(count = result.playlists?.size ?: 0) {
            HorizontalWithTitleThumbnail(
                imageUrl = "",
                title = result.playlists?.get(it)?.name ?: "",
                description = "Playlist",
                onClick = { onPlaylistClick(result.playlists?.get(it)?.id ?: "") },
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}
