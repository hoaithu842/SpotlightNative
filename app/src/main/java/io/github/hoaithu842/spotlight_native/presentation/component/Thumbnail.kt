package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.extension.noRippleClickable
import io.github.hoaithu842.spotlight_native.ui.designsystem.OuterBox
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme

enum class ThumbnailCoverSize(val size: Dp) {
    MEDIUM(size = SpotlightDimens.RecentSectionThumbnailSize),
    LARGE(size = SpotlightDimens.RecommendationSectionThumbnailSize),
}

@Composable
fun VerticalThumbnail() {
}

@Composable
fun VerticalRoundedCornerThumbnail(
    imageUrl: String,
    description: String,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
    thumbnailCoverSize: ThumbnailCoverSize = ThumbnailCoverSize.LARGE,
) {
    OuterBox(
        onLongPress = onLongPress,
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            RoundedCornerCover(
                imageUrl = imageUrl,
                modifier = Modifier.size(thumbnailCoverSize.size),
            )

            Text(
                text = description,
                style = SpotlightTextStyle.Text11W400,
                color = NavigationGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier =
                    Modifier
                        .padding(top = 8.dp)
                        .width(thumbnailCoverSize.size),
            )
        }
    }
}

@Composable
fun VerticalWithTitleThumbnail(
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    thumbnailCoverSize: ThumbnailCoverSize = ThumbnailCoverSize.LARGE,
) {
    Column(
        modifier =
            modifier
                .noRippleClickable {
                    onClick()
                }
                .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Cover(
            imageUrl = imageUrl,
            modifier = Modifier.size(thumbnailCoverSize.size),
        )

        Text(
            text = title,
            style = SpotlightTextStyle.Text12W600,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(thumbnailCoverSize.size),
        )

        Text(
            text = description,
            style = SpotlightTextStyle.Text11W400,
            color = NavigationGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(thumbnailCoverSize.size),
        )
    }
}

@Composable
fun VerticalRoundedCornerWithTitleThumbnail(
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    thumbnailCoverSize: ThumbnailCoverSize = ThumbnailCoverSize.LARGE,
) {
    Column(
        modifier =
            modifier
                .padding(8.dp)
                .noRippleClickable {
                    onClick()
                },
    ) {
        Cover(
            imageUrl = imageUrl,
            modifier = Modifier.size(thumbnailCoverSize.size),
        )

        Text(
            text = title,
            style = SpotlightTextStyle.Text12W600,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(thumbnailCoverSize.size),
        )

        Text(
            text = description,
            style = SpotlightTextStyle.Text11W400,
            color = NavigationGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(thumbnailCoverSize.size),
        )
    }
}

@Composable
fun VerticalCircularWithTitleThumbnail(
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    thumbnailCoverSize: ThumbnailCoverSize = ThumbnailCoverSize.LARGE,
) {
    Column(
        modifier =
            modifier
                .padding(8.dp)
                .noRippleClickable {
                    onClick()
                },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularCover(
            imageUrl = imageUrl,
            modifier = Modifier.size(thumbnailCoverSize.size),
            contentScale = ContentScale.Crop,
        )

        Text(
            text = title,
            style = SpotlightTextStyle.Text12W600,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(thumbnailCoverSize.size),
        )

        Text(
            text = description,
            style = SpotlightTextStyle.Text11W400,
            color = NavigationGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(thumbnailCoverSize.size),
        )
    }
}

@Composable
fun HorizontalCircularThumbnail(
    imageUrl: String,
    artist: String,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OuterBox(
        onLongPress = onLongPress,
        modifier = modifier,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(SpotlightDimens.ThumbnailSize)
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularCover(
                imageUrl = imageUrl,
                modifier =
                    Modifier
                        .size(SpotlightDimens.ThumbnailSize)
                        .clip(shape = RoundedCornerShape(size = 6.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(1.dp),
            )
            Text(
                text = artist,
                style = SpotlightTextStyle.Text11W600,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationTextPadding)
                        .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun HorizontalRoundedCornerThumbnail(
    imageUrl: String,
    artist: String,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OuterBox(
        onLongPress = onLongPress,
        modifier = modifier,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(SpotlightDimens.ThumbnailSize)
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundedCornerCover(
                imageUrl = imageUrl,
                modifier = Modifier.size(SpotlightDimens.ThumbnailSize),
            )
            Text(
                text = artist,
                style = SpotlightTextStyle.Text11W600,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationTextPadding)
                        .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun HorizontalWithTitleThumbnail(
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.LibraryItemHeight)
                .noRippleClickable(onClick),
    ) {
        Cover(
            imageUrl = imageUrl,
            modifier = Modifier.size(SpotlightDimens.LibraryItemHeight),
        )
        Column(
            modifier =
                Modifier
                    .padding(horizontal = SpotlightDimens.LibraryTextPadding)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = SpotlightTextStyle.Text16W400,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = description,
                style = SpotlightTextStyle.Text11W400,
                color = NavigationGray,
                modifier = Modifier.padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f)),
            )
        }
    }
}

@Composable
fun HorizontalCircularWithTitleThumbnail(
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.LibraryItemHeight)
                .noRippleClickable(onClick),
    ) {
        CircularCover(
            imageUrl = imageUrl,
            modifier = Modifier.size(SpotlightDimens.LibraryItemHeight),
        )
        Column(
            modifier =
                Modifier
                    .padding(horizontal = SpotlightDimens.LibraryTextPadding)
                    .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = SpotlightTextStyle.Text16W400,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = description,
                style = SpotlightTextStyle.Text11W400,
                color = NavigationGray,
                modifier = Modifier.padding(top = SpotlightDimens.LibraryTextPadding.times(0.5f)),
            )
        }
    }
}

@Preview
@Composable
fun AllThumbnailPreviews() {
    SpotlightTheme {
        Column {
            val imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp"
            val artist = "Justatee"
            val description = "The Weeknd, Lady Gaga, JENNIE, Charlie Puth, yung kai, Dhruv"

            VerticalRoundedCornerThumbnail(
                imageUrl = imageUrl,
                description = description,
                onClick = {},
                onLongPress = {},
            )

            VerticalWithTitleThumbnail(
                imageUrl = imageUrl,
                title = "Yen",
                description = description,
                onClick = {},
            )

            VerticalRoundedCornerWithTitleThumbnail(
                imageUrl = imageUrl,
                title = "Yen",
                description = description,
                onClick = {},
            )

            VerticalCircularWithTitleThumbnail(
                imageUrl = imageUrl,
                title = "Yen",
                description = description,
                onClick = {},
            )

            HorizontalCircularThumbnail(
                imageUrl = imageUrl,
                artist = artist,
                onLongPress = {},
            )

            HorizontalRoundedCornerThumbnail(
                imageUrl = imageUrl,
                artist = artist,
                onLongPress = {},
            )
        }
    }
}
