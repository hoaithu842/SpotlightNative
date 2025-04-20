package io.github.hoaithu842.spotlight.presentation.screen

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.hoaithu842.spotlight.domain.model.Image
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.presentation.component.SongItem
import io.github.hoaithu842.spotlight.presentation.viewmodel.LibraryFavoriteUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.LibraryFavoriteViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.DotsCollision
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryFavoriteScreen(
    onCancelClick: () -> Unit,
    viewModel: LibraryFavoriteViewModel = hiltViewModel(),
) {
    val uiState by viewModel.libraryFavoriteUiState.collectAsStateWithLifecycle()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val displayMetrics: DisplayMetrics = LocalContext.current.resources.displayMetrics
    val dpWidth = 200.dp

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .background(MaterialTheme.colorScheme.surface),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
        ) {
            HeadlineCover(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(dpWidth * (1 - scrollBehavior.state.collapsedFraction) * 0.9f),
                collapsedFraction = scrollBehavior.state.collapsedFraction,
            )

            Headline(
                artistName = "Liked Songs",
                expandedHeight = dpWidth,
                scrollBehavior = scrollBehavior,
                onBackClick = onCancelClick,
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(bottom = SpotlightDimens.MinimizedPlayerHeight),
        ) {
            when (uiState) {
                LibraryFavoriteUiState.Error -> {}
                LibraryFavoriteUiState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            DotsCollision()
                        }
                    }
                }

                is LibraryFavoriteUiState.Success -> {
                    items(
                        (uiState as LibraryFavoriteUiState.Success).items.size,
                    ) { index ->
                        val item =
                            (uiState as LibraryFavoriteUiState.Success).items[index]
                        SongItem(
                            song =
                                Song(
                                    name = item.title ?: "",
                                    url = item.url ?: "",
                                    id = item.id ?: "",
                                ),
                            cover = item.image ?: Image(),
                            artists =
                                item.artists?.joinToString(separator = ", ") { it.name }
                                    ?: "Song",
                            modifier = Modifier.padding(start = 10.dp, top = 5.dp, bottom = 5.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeadlineCover(
    modifier: Modifier = Modifier,
    collapsedFraction: Float,
) {
    val gradientBrush =
        Brush.verticalGradient(
            colors =
                listOf(
                    MinimizedPlayerBackground.copy(alpha = 1f - collapsedFraction),
                    MaterialTheme.colorScheme.surface.copy(alpha = collapsedFraction),
                ),
        )

    Spacer(
        modifier =
            modifier
                .background(
                    brush = gradientBrush,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Headline(
    artistName: String,
    expandedHeight: Dp,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = artistName,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                )
            }
        },
        colors =
            TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.surface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            ),
        scrollBehavior = scrollBehavior,
        expandedHeight = expandedHeight,
        collapsedHeight = 0.3 * 100.dp,
    )
}
