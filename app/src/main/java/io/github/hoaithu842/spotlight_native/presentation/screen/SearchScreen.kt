package io.github.hoaithu842.spotlight_native.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.hoaithu842.spotlight_native.presentation.component.ArtistRecommendation
import io.github.hoaithu842.spotlight_native.presentation.component.BrowseItem
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SearchTopAppBar
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightDimens

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    onAvatarClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        SearchTopAppBar(
            onAvatarClick = onAvatarClick,
            modifier = Modifier
                .statusBarsPadding()
                .height(SpotlightDimens.TopAppBarHeight)
        )

        FlowRow(
            maxItemsInEachRow = 2,
            modifier = Modifier.padding(SpotlightDimens.RecommendationPadding)
        ) {
            BrowseItem(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseItem(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseItem(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseItem(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
            BrowseItem(
                title = "Musics",
                imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(SpotlightDimens.RecommendationPadding),
            )
        }
    }
}