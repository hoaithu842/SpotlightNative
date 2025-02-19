package io.github.hoaithu842.spotlight_native.presentation.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.R
import io.github.hoaithu842.spotlight_native.extension.noRippleClickable
import io.github.hoaithu842.spotlight_native.presentation.designsystem.FullsizePlayerTopAppBar
import io.github.hoaithu842.spotlight_native.presentation.designsystem.PlayerControllerTopAppBar
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.presentation.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight_native.presentation.theme.NavigationGray
import io.github.hoaithu842.spotlight_native.presentation.theme.ProgressIndicatorColor
import io.github.hoaithu842.spotlight_native.presentation.theme.ProgressIndicatorTrackColor
import io.github.hoaithu842.spotlight_native.presentation.theme.SpotlightTheme
import kotlinx.coroutines.launch

@Composable
fun FullsizePlayer(
    isPlaying: Boolean,
    songName: String,
    artists: String,
    onMinimizeClick: () -> Unit,
    onMainFunctionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    val shouldDisplayTopAppBar by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 1
        }
    }
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .background(MinimizedPlayerBackground)
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
        ) {
            item {
                FullsizePlayerTopAppBar(
                    artists = "Đen",
                    onMinimizeClick = onMinimizeClick,
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = SpotlightDimens.FullsizePlayerTopAppBarPadding)
                )
            }
            item {
                MainPlayerContent(
                    isPlaying = isPlaying,
                    songName = songName,
                    artists = artists,
                    onMainFunctionClick = onMainFunctionClick,
                )
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                    Text(
                        text = "Some text",
                        modifier = Modifier.padding(vertical = SpotlightDimens.MinimizedPlayerHeight)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = shouldDisplayTopAppBar,
            enter = slideInVertically(),
            exit = fadeOut(),
        ) {
            PlayerControllerTopAppBar(
                songName = songName,
                artists = artists,
                isPlaying = isPlaying,
                onMainFunctionClick = onMainFunctionClick,
                onPlayerClick = {
                    scope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                },
            )
        }
    }
}

@Composable
fun MainPlayerContent(
    isPlaying: Boolean,
    songName: String,
    artists: String,
    onMainFunctionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = SpotlightDimens.FullsizePlayerMainContentHorizontalPadding)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .padding(vertical = SpotlightDimens.FullsizePlayerMainContentPadding)
                .size(SpotlightDimens.FullsizePlayerThumbnailSize),
            contentScale = ContentScale.Crop, // TODO: replace later
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(end = SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize.times(2))
            ) {
                Text(
                    text = songName,
                    style = SpotlightTextStyle.Text22W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .basicMarquee()
                )
                Text(
                    text = artists,
                    style = SpotlightTextStyle.Text16W400,
                    overflow = TextOverflow.Ellipsis,
                    color = NavigationGray,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Icon(
                painter = painterResource(SpotlightIcons.Heart),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                    .align(Alignment.CenterEnd)
                    .noRippleClickable { }
            )
        }

        LinearProgressIndicator(
            progress = { 0.2f },
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            color = ProgressIndicatorTrackColor,
            trackColor = ProgressIndicatorColor,
            strokeCap = StrokeCap.Round,
        )

        Row(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "0:00",
                style = SpotlightTextStyle.Text11W400,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = "-1:18",
                style = SpotlightTextStyle.Text11W400,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        PlayerController(
            isPlaying = isPlaying,
            onMainFunctionClick = onMainFunctionClick,
            modifier = Modifier.padding(vertical = 14.dp)
        )
    }
}

@Composable
fun PlayerController(
    isPlaying: Boolean,
    onMainFunctionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            painter = painterResource(SpotlightIcons.Shuffle),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(SpotlightDimens.PlayerControllerSmallIconSize)
                .noRippleClickable { },
        )
        Icon(
            painter = painterResource(SpotlightIcons.PlayPrevious),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(SpotlightDimens.PlayerControllerMediumIconSize)
                .noRippleClickable { },
        )
        Icon(
            painter = painterResource(if (isPlaying) SpotlightIcons.Pause else SpotlightIcons.Play),
            contentDescription = "",
            tint = MinimizedPlayerBackground,
            modifier = Modifier
                .size(SpotlightDimens.PlayerControllerLargeIconSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onBackground)
                .padding((SpotlightDimens.PlayerControllerLargeIconSize - SpotlightDimens.PlayerControllerMediumIconSize) / 2)
                .noRippleClickable {
                    onMainFunctionClick()
                },
        )
        Icon(
            painter = painterResource(SpotlightIcons.PlayNext),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(SpotlightDimens.PlayerControllerMediumIconSize)
                .noRippleClickable { },
        )
        Icon(
            painter = painterResource(SpotlightIcons.Timer),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .size(SpotlightDimens.PlayerControllerSmallIconSize)
                .noRippleClickable { },
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FullsizePlayerPreview() {
    SpotlightTheme {
        FullsizePlayer(
            isPlaying = true,
            songName = "Merry Go Round of Life (From Howl's Moving Castle Original Motion Picture Soundtrack)",
            artists = " Grissini Project",
            onMinimizeClick = {},
            onMainFunctionClick = {},
        )
    }
}