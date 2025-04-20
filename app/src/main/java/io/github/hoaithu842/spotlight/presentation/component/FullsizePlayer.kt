package io.github.hoaithu842.spotlight.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.extension.shimmerLoadingAnimation
import io.github.hoaithu842.spotlight.extension.toTimeFormat
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight.ui.theme.ProgressIndicatorColor
import io.github.hoaithu842.spotlight.ui.theme.ProgressIndicatorTrackColor

@Composable
fun MainPlayerContent(
    isPlaying: Boolean,
    isFavorite: Boolean,
    song: SongInfo?,
    currentPosition: Long,
    duration: Long,
    painter: AsyncImagePainter,
    isLoading: Boolean,
    onPrevClick: () -> Unit,
    onMainFunctionClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
//            .padding(horizontal = SpotlightDimens.FullsizePlayerMainContentHorizontalPadding)
                .fillMaxWidth()
                .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painter,
            contentDescription = "",
            modifier =
                Modifier
                    .padding(vertical = SpotlightDimens.FullsizePlayerMainContentPadding)
                    .size(SpotlightDimens.FullsizePlayerThumbnailSize)
                    .shimmerLoadingAnimation(
                        isLoadingCompleted = !isLoading,
                        isLightModeActive = !isSystemInDarkTheme(),
                    ),
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
        ) {
            Column(
                modifier =
                    Modifier
                        .wrapContentHeight()
                        .padding(end = SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize.times(2)),
            ) {
                Text(
                    text = song?.title.toString(),
                    style = SpotlightTextStyle.Text22W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .basicMarquee(),
                )
                Text(
                    text = song?.artists?.joinToString(separator = ", ") { it.name } ?: "",
                    style = SpotlightTextStyle.Text16W400,
                    overflow = TextOverflow.Ellipsis,
                    color = NavigationGray,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Icon(
                painter = painterResource(if (isFavorite) SpotlightIcons.HeartSelected else SpotlightIcons.Heart),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier =
                    Modifier
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                        .align(Alignment.CenterEnd)
                        .noRippleClickable { },
            )
        }

        LinearProgressIndicator(
            progress = { if (duration.toInt() == 0) 0f else (currentPosition * 1.0 / duration).toFloat() },
            modifier =
                Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
            color = ProgressIndicatorTrackColor,
            trackColor = ProgressIndicatorColor,
            strokeCap = StrokeCap.Round,
        )

        Row(
            modifier =
                Modifier
                    .padding(top = 6.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = currentPosition.toTimeFormat(),
                style = SpotlightTextStyle.Text11W400,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = duration.toTimeFormat(),
                style = SpotlightTextStyle.Text11W400,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
        PlayerController(
            isPlaying = isPlaying,
            onPrevClick = onPrevClick,
            onMainFunctionClick = onMainFunctionClick,
            onNextClick = onNextClick,
            modifier = Modifier.padding(vertical = 14.dp),
        )
    }
}

@Composable
fun PlayerController(
    isPlaying: Boolean,
    onPrevClick: () -> Unit,
    onMainFunctionClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            painter = painterResource(SpotlightIcons.Shuffle),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier =
                Modifier
                    .size(SpotlightDimens.PlayerControllerSmallIconSize)
                    .noRippleClickable { },
        )
        Icon(
            painter = painterResource(SpotlightIcons.PlayPrevious),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier =
                Modifier
                    .size(SpotlightDimens.PlayerControllerMediumIconSize)
                    .noRippleClickable {
                        onPrevClick()
                    },
        )
        Icon(
            painter = painterResource(if (isPlaying) SpotlightIcons.Pause else SpotlightIcons.Play),
            contentDescription = "",
            tint = MinimizedPlayerBackground,
            modifier =
                Modifier
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
            modifier =
                Modifier
                    .size(SpotlightDimens.PlayerControllerMediumIconSize)
                    .noRippleClickable {
                        onNextClick()
                    },
        )
        Icon(
            painter = painterResource(SpotlightIcons.Timer),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier =
                Modifier
                    .size(SpotlightDimens.PlayerControllerSmallIconSize)
                    .noRippleClickable { },
        )
    }
}
