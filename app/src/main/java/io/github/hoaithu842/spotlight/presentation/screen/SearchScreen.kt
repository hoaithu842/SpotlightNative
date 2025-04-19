package io.github.hoaithu842.spotlight.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hoaithu842.spotlight.domain.model.UserProfile
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.presentation.component.BrowseCard
import io.github.hoaithu842.spotlight.presentation.component.SearchBar
import io.github.hoaithu842.spotlight.presentation.viewmodel.SearchUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.SearchViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.SearchTopAppBar
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    userProfile: UserProfile?,
    onAvatarClick: () -> Unit,
    onNavigateToSearchClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.searchUiState.collectAsState()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        SearchTopAppBar(
            scrollBehavior = scrollBehavior,
            onAvatarClick = onAvatarClick,
            avatarUrl = userProfile?.pictureURL,
        )

        SearchBar(
            modifier =
                Modifier
                    .padding(
                        start = SpotlightDimens.RecommendationPadding * 2,
                        end = SpotlightDimens.RecommendationPadding * 2,
                        top = SpotlightDimens.SearchBarTopPadding,
                        bottom = SpotlightDimens.TopAppBarHorizontalPadding * 2,
                    )
                    .noRippleClickable { onNavigateToSearchClick() },
        )

        when (uiState) {
            SearchUiState.Error -> Text(text = "Error")
            SearchUiState.Loading -> Text(text = "Loading")
            is SearchUiState.Success -> {
                FlowRow(
                    maxItemsInEachRow = 2,
                    modifier =
                        Modifier
                            .padding(SpotlightDimens.RecommendationPadding)
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = SpotlightDimens.MinimizedPlayerHeight),
                ) {
                    (uiState as SearchUiState.Success).playlists.items?.forEach {
                        BrowseCard(
                            title = it.name ?: "",
                            imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                            modifier =
                                Modifier
                                    .fillMaxWidth(0.5f)
                                    .padding(SpotlightDimens.RecommendationPadding),
                        )
                    }
                }
            }
        }
    }
}
