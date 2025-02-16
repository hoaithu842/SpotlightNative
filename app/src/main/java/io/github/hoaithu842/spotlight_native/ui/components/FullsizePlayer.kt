package io.github.hoaithu842.spotlight_native.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme

@Composable
fun FullsizePlayer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MinimizedPlayerBackground)
    ) {
        FullsizePlayerTopAppBar(
            artist = "Đen",
            modifier = Modifier.padding(horizontal = SpotlightDimens.FullsizePlayerTopAppBarPadding)
        )
    }
}

@Composable
fun FullsizePlayerTopAppBar(
    artist: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SpotlightDimens.FullsizePlayerTopAppBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            painter = painterResource(SpotlightIcons.Down),
            contentDescription = "",
            modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = artist,
            style = SpotlightTextStyle.Text11W400,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = SpotlightDimens.FullsizePlayerTopAppBarPadding),
        )
        Icon(
            painter = painterResource(SpotlightIcons.More),
            contentDescription = "",
            modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FullsizePlayerPreview() {
    SpotlightTheme {
        FullsizePlayer()
    }
}