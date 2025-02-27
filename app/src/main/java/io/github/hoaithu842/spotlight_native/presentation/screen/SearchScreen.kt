package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import io.github.hoaithu842.spotlight_native.extension.noRippleClickable
import io.github.hoaithu842.spotlight_native.presentation.component.BrowseThumbnail
import io.github.hoaithu842.spotlight_native.presentation.component.SearchBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SearchTopAppBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onAvatarClick: () -> Unit,
    onNavigateToSearchClick: () -> Unit,
) {
    val searchQuery by remember { mutableStateOf("") }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        SearchTopAppBar(
            scrollBehavior = scrollBehavior,
            onAvatarClick = onAvatarClick,
        )

        SearchBar(
            modifier = Modifier
                .padding(
                    start = SpotlightDimens.RecommendationPadding * 2,
                    end = SpotlightDimens.RecommendationPadding * 2,
                    top = SpotlightDimens.SearchBarTopPadding,
                    bottom = SpotlightDimens.TopAppBarHorizontalPadding * 2,
                )
                .noRippleClickable { onNavigateToSearchClick() }
        )


        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier
                .padding(SpotlightDimens.RecommendationPadding)
                .verticalScroll(rememberScrollState())
                .padding(bottom = SpotlightDimens.MinimizedPlayerHeight),
        ) {
            BrowseThumbnail(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseThumbnail(
                title = "Musicsjfglisajglkjsdflkgjads;lgj;lsajdg;ljsadl;gjl;sa",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
        }
    }
}