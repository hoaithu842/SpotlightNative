package io.github.hoaithu842.spotlight.ui.designsystem

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight.R
import io.github.hoaithu842.spotlight.domain.model.UserProfile
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.presentation.component.CircularCover
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight.ui.theme.SpotlightTheme
import io.github.hoaithu842.spotlight.ui.theme.TopAppBarGray

enum class CustomDrawerState {
    Opened,
    Closed,
}

fun CustomDrawerState.isOpened(): Boolean {
    return this.name == "Opened"
}

fun CustomDrawerState.opposite(): CustomDrawerState {
    return if (this == CustomDrawerState.Opened) {
        CustomDrawerState.Closed
    } else {
        CustomDrawerState.Opened
    }
}

@Composable
fun HomeScreenDrawer(
    userProfile: UserProfile?,
    onProfileClick: () -> Unit,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(TopAppBarGray)
                .safeDrawingPadding(),
    ) {
        HomeScreenDrawerHeader(
            userProfile = userProfile,
            onProfileClick = onProfileClick,
            onLoginClick = onLoginClick,
            onLogoutClick = onLogoutClick,
            modifier = Modifier.padding(vertical = SpotlightDimens.HomeScreenDrawerHeaderVerticalPadding),
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
    userProfile: UserProfile?,
    onProfileClick: () -> Unit,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .padding(horizontal = SpotlightDimens.HomeScreenDrawerHeaderPadding)
                .fillMaxWidth()
                .height(SpotlightDimens.TopAppBarHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (userProfile == null) {
            Text(
                text = "Login",
                modifier = Modifier.noRippleClickable(onLoginClick),
                color = MaterialTheme.colorScheme.onBackground,
            )
        } else {
            if (userProfile.pictureURL != null) {
                CircularCover(
                    imageUrl = userProfile.pictureURL!!,
                    modifier =
                        Modifier
                            .size(SpotlightDimens.HomeScreenDrawerHeaderIconSize)
                            .clickable { onProfileClick() },
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.person),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .size(SpotlightDimens.HomeScreenDrawerHeaderIconSize)
                            .clip(shape = CircleShape)
                            .clickable { onProfileClick() },
                )
            }

            Column(
                modifier = Modifier.padding(start = SpotlightDimens.HomeScreenDrawerHeaderPadding),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = userProfile.name ?: "undefined name",
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
                    modifier = Modifier.noRippleClickable(onProfileClick),
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding)
                        .noRippleClickable(onLogoutClick),
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
        modifier =
            modifier
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

@Preview
@Composable
fun HomeScreenDrawerPreview() {
    SpotlightTheme {
        HomeScreenDrawer(
            userProfile = null,
            onProfileClick = {},
            onLoginClick = {},
            onLogoutClick = {},
        )
    }
}
