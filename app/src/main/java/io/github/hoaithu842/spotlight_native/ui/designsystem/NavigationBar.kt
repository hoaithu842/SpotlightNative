package io.github.hoaithu842.spotlight_native.ui.designsystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray

@Composable
fun SpotlightNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        content()
    }
}

@Composable
fun SpotlightNavigationBarItem(
    title: String,
    selected: Boolean,
    icon: Int,
    selectedIcon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                Icon(
                    painter = painterResource(selectedIcon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(SpotlightDimens.NavigationBarIconSize),
                )
            } else {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    tint = NavigationGray,
                    modifier = Modifier.size(SpotlightDimens.NavigationBarIconSize),
                )
            }
        },
        label = {
            Text(
                text = title,
                style = SpotlightTextStyle.Text11W400,
                color = if (!selected) NavigationGray else MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
            )
        },
        colors = NavigationRailItemDefaults.colors(
            indicatorColor = Color.Transparent
//            selectedIconColor = selectedIconColor,
//            selectedTextColor = selectedTextColor,
//            selectedIndicatorColor = indicatorColor,
//            unselectedIconColor = unselectedIconColor,
//            unselectedTextColor = unselectedTextColor,
//            disabledIconColor = disabledIconColor,
//            disabledTextColor = disabledTextColor,
        ),
        modifier = modifier,
    )
}