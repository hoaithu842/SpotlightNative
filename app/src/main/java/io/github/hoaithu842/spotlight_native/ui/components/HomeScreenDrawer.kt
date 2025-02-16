package io.github.hoaithu842.spotlight_native.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.R
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight_native.ui.theme.TopAppBarGray

@Composable
fun HomeScreenDrawer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(TopAppBarGray)
            .safeDrawingPadding()
    ) {
        HomeScreenDrawerHeader(
            username = "Hoai Thu",
            onAvatarClick = {},
            modifier = Modifier.padding(vertical = SpotlightDimens.HomeScreenDrawerHeaderVerticalPadding)
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.15.dp,
            color = NavigationGray,
        )

        HomeScreenDrawerOption(
            icon = SpotlightIcons.Add,
            title = stringResource(R.string.add_account),
            onOptionClick = {},
        )

        HomeScreenDrawerOption(
            icon = SpotlightIcons.Lightning,
            title = stringResource(R.string.whats_new),
            onOptionClick = {},
        )

        HomeScreenDrawerOption(
            icon = SpotlightIcons.Clock,
            title = stringResource(R.string.recents),
            onOptionClick = {},
        )

        HomeScreenDrawerOption(
            icon = SpotlightIcons.Settings,
            title = stringResource(R.string.settings_and_privacy),
            onOptionClick = {},
        )
    }
}

@Composable
fun HomeScreenDrawerHeader(
    username: String,
    onAvatarClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = SpotlightDimens.HomeScreenDrawerHeaderPadding)
            .fillMaxWidth()
            .height(SpotlightDimens.TopAppBarHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .size(SpotlightDimens.HomeScreenDrawerHeaderIconSize)
                .clip(shape = CircleShape)
                .clickable { onAvatarClick() }
        )

        Column(
            modifier = Modifier.padding(start = SpotlightDimens.HomeScreenDrawerHeaderPadding),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = username,
                style = SpotlightTextStyle.Text18W700,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
            )
            Text(
                text = stringResource(R.string.view_profile),
                style = SpotlightTextStyle.Text11W400,
                overflow = TextOverflow.Ellipsis,
                color = NavigationGray,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun HomeScreenDrawerOption(
    icon: Int,
    title: String,
    onOptionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(SpotlightDimens.HomeScreenDrawerHeaderPadding)
            .fillMaxWidth()
            .height(SpotlightDimens.HomeScreenDrawerHeaderOptionHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = title,
            style = SpotlightTextStyle.Text14W400,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            modifier = Modifier.padding(start = SpotlightDimens.HomeScreenDrawerHeaderPadding),
        )
    }
}