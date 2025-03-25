package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.LyricsCardBackgroundColor
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme
import io.github.hoaithu842.spotlight_native.ui.theme.TopAppBarGray

@Composable
fun BrowseCard(
    title: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.BrowseSectionHeight)
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        Text(
            text = title,
            style = SpotlightTextStyle.Text16W600,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .height(SpotlightDimens.BrowseSectionHeight)
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = SpotlightDimens.BrowseSectionThumbnailSize,
                )
        )
        RoundedCornerCover(
            imageUrl = imageUrl,
            modifier = modifier
                .align(Alignment.BottomEnd)
                .size(SpotlightDimens.BrowseSectionThumbnailSize)
                .rotate(30f)
                .offset(
                    x = SpotlightDimens.BrowseSectionThumbnailXOffset,
                    y = SpotlightDimens.BrowseSectionThumbnailYOffset,
                ),
        )
    }
}

@Composable
fun LyricsCard(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.LyricsCardHeight)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(LyricsCardBackgroundColor),
    ) {
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            style = SpotlightTextStyle.Text18W500L30,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 50.dp,
                    horizontal = 10.dp
                )
                .verticalScroll(
// enabled = false,
                    state = rememberScrollState(),
                )
        )

        Column {
            Text(
                text = "Lyrics preview",
                style = SpotlightTextStyle.Text14W600,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .fillMaxWidth()
                    .blur(15.dp, BlurredEdgeTreatment.Unbounded)
                    .background(LyricsCardBackgroundColor.copy(alpha = 1f))
            )
        }

        Column(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .height(15.dp)
                    .fillMaxWidth()
                    .blur(15.dp, BlurredEdgeTreatment.Unbounded)
                    .background(LyricsCardBackgroundColor.copy(alpha = 1f))
            )
            Text(
                text = "Show lyrics",
                style = SpotlightTextStyle.Text12W600,
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50.dp))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(
                        vertical = 7.dp,
                        horizontal = 13.dp,
                    ),
            )
        }
    }
}

@Composable
fun ArtistCard(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.ArtistCardHeight)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(TopAppBarGray),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Cover(
                imageUrl = imageUrl,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(10.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Text(
                            text = "Jax",
                            style = SpotlightTextStyle.Text16W600,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Text(
                            text = "2.5M monthly listeners",
                            style = SpotlightTextStyle.Text11W400,
                            color = NavigationGray,
                        )
                    }
                    Text(
                        text = "Follow",
                        style = SpotlightTextStyle.Text12W600,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(50.dp))
                            .background(TopAppBarGray)
                            .border(
                                width = 0.5.dp,
                                shape = RoundedCornerShape(50.dp),
                                color = NavigationGray,
                            )
                            .padding(
                                vertical = 7.dp,
                                horizontal = 13.dp,
                            ),
                    )
                }

                Text(
                    text = "my manager wrote my bio, I told him he should kill off the main character instead.",
                    minLines = 2,
                    maxLines = 2,
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(TopAppBarGray),
                )
            }
        }
        Text(
            text = "About the artist",
            style = SpotlightTextStyle.Text14W600,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.TopStart),
        )
    }
}

@Composable
fun CreditCard(
    creditsList: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.CreditCardHeight)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(TopAppBarGray),
    ) {
        Text(
            text = "Credits",
            style = SpotlightTextStyle.Text14W600,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
        )

        creditsList.forEach {
            Column(
                modifier = Modifier.padding(
                    horizontal = 15.dp,
                    vertical = 8.dp,
                )
            ) {
                Text(
                    text = it.first,
                    style = SpotlightTextStyle.Text14W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                )
                Text(
                    text = it.second,
                    style = SpotlightTextStyle.Text11W400,
                    color = NavigationGray,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    SpotlightTheme {
        BrowseCard(
            title = "Musics",
            imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
            modifier = Modifier
        )
    }
}