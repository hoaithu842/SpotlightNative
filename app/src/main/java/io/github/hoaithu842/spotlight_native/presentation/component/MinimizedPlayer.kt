package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.R
import io.github.hoaithu842.spotlight_native.domain.model.Song
import io.github.hoaithu842.spotlight_native.extension.noRippleClickable
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.presentation.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight_native.presentation.theme.NavigationGray
import io.github.hoaithu842.spotlight_native.presentation.theme.ProgressIndicatorColor
import io.github.hoaithu842.spotlight_native.presentation.theme.ProgressIndicatorTrackColor

@Composable
fun MinimizedPlayer(
    isPlaying: Boolean,
    song: Song,
    currentPosition: Long,
    duration: Long,
    onPlayerClick: () -> Unit,
    onMainFunctionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.MinimizedPlayerHeight)
            .clip(
                shape = RoundedCornerShape(size = 12.dp)
            )
            .background(MinimizedPlayerBackground)
            .clickable {
                onPlayerClick()
            }
            .padding(bottom = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SpotlightDimens.MinimizedPlayerThumbnailPaddingStart),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize.times(2)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier = Modifier
                        .size(SpotlightDimens.MinimizedPlayerThumbnailSize)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                )

                Column(
                    modifier = Modifier.padding(start = SpotlightDimens.MinimizedPlayerInfoPaddingStart)
                ) {
                    Text(
                        text = song.title,
                        style = SpotlightTextStyle.Text11W400,
                        color = MaterialTheme.colorScheme.onBackground,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .basicMarquee()
                    )
                    Text(
                        text = song.artists,
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
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                    .align(Alignment.CenterEnd)
                    .noRippleClickable {
                        onMainFunctionClick()
                    },
            )
        }

        LinearProgressIndicator(
            progress = { if (duration.toInt() == 0) 0f else (currentPosition * 1.0 / duration).toFloat() },
            modifier = Modifier
                .padding(horizontal = SpotlightDimens.MinimizedPlayerProgressIndicatorPadding)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = ProgressIndicatorTrackColor,
            trackColor = ProgressIndicatorColor,
            strokeCap = StrokeCap.Round,
        )
    }
}