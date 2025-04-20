package io.github.hoaithu842.spotlight.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import io.github.hoaithu842.spotlight.domain.model.SongInfo
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.extension.shimmerLoadingAnimation
import io.github.hoaithu842.spotlight.extension.toColor
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight.ui.theme.ProgressIndicatorColor
import io.github.hoaithu842.spotlight.ui.theme.ProgressIndicatorTrackColor

@Composable
fun MinimizedPlayer(
    isPlaying: Boolean,
    song: SongInfo?,
    currentPosition: Long,
    duration: Long,
    painter: AsyncImagePainter,
    isLoading: Boolean,
    onPlayerClick: () -> Unit,
    onMainFunctionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.MinimizedPlayerHeight)
                .clip(
                    shape = RoundedCornerShape(size = 12.dp),
                )
                .background(
                    brush =
                        Brush.horizontalGradient(
                            colors =
                                listOf(
                                    song?.color?.toColor() ?: MaterialTheme.colorScheme.surface,
                                    Color.Black,
                                ),
                        ),
                )
                .clickable {
                    onPlayerClick()
                },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = SpotlightDimens.MinimizedPlayerThumbnailPaddingStart),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(end = SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize.times(2)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier =
                        Modifier
                            .size(SpotlightDimens.MinimizedPlayerThumbnailSize)
                            .clip(shape = RoundedCornerShape(size = 6.dp))
                            .shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                isLightModeActive = !isSystemInDarkTheme(),
                            ),
                )

                Column(
                    modifier = Modifier.padding(start = SpotlightDimens.MinimizedPlayerInfoPaddingStart),
                ) {
                    Text(
                        text = song?.title.toString(),
                        style = SpotlightTextStyle.Text11W400,
                        color = Color.White,
                        maxLines = 1,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .basicMarquee(),
                    )
                    Text(
                        text = song?.artists?.joinToString(separator = ", ") { it.name } ?: "",
                        style = SpotlightTextStyle.Text11W400,
                        overflow = TextOverflow.Ellipsis,
                        color = NavigationGray,
                        maxLines = 1,
                    )
                }
            }

            Icon(
                painter = painterResource(if (isPlaying) SpotlightIcons.Pause else SpotlightIcons.Play),
                contentDescription = "",
                tint = Color.White,
                modifier =
                    Modifier
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                        .align(Alignment.CenterEnd)
                        .noRippleClickable {
                            onMainFunctionClick()
                        },
            )
        }

        LinearProgressIndicator(
            progress = { if (duration.toInt() == 0) 0f else (currentPosition * 1.0 / duration).toFloat() },
            modifier =
                Modifier
                    .padding(horizontal = SpotlightDimens.MinimizedPlayerProgressIndicatorPadding)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            color = ProgressIndicatorTrackColor,
            trackColor = ProgressIndicatorColor,
            strokeCap = StrokeCap.Round,
        )
    }
}
