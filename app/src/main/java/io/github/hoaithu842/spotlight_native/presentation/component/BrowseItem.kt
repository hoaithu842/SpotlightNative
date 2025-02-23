package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme
import io.github.hoaithu842.spotlight_native.ui.theme.TopAppBarGray

@Composable
fun BrowseItem(
    title: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(SpotlightDimens.BrowseSectionHeight)
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .background(TopAppBarGray),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = title,
            style = SpotlightTextStyle.Text16W600,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .height(SpotlightDimens.BrowseSectionHeight)
                .weight(2f)
                .padding(16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalAlignment = Alignment.Bottom,
        ) {
            EPThumbnail(
                imageUrl = imageUrl,
                modifier = modifier
                    .rotate(30f)
                    .fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    SpotlightTheme {
        BrowseItem(
            title = "Musics",
            imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
            modifier = Modifier
        )
    }
}