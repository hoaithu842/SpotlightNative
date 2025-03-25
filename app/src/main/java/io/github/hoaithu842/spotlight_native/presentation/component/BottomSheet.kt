package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenDrawerHeader
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = Color.DarkGray,
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            HomeScreenDrawerHeader(
                userProfile = null,
                onProfileClick = {},
                onLoginClick = {},
                onLogoutClick = {},
            )

            HorizontalDivider(
                thickness = 0.15.dp,
                color = NavigationGray,
                modifier = Modifier
                    .padding(vertical = SpotlightDimens.HomeScreenDrawerHeaderVerticalPadding)
                    .fillMaxWidth(),
            )

            ModalBottomSheetOption(
                icon = SpotlightIcons.Add,
                title = "Add to Your Library",
                modifier = Modifier.padding(horizontal = SpotlightDimens.HomeScreenDrawerHeaderPadding)
            )

            ModalBottomSheetOption(
                icon = SpotlightIcons.Download,
                title = "Download",
                modifier = Modifier.padding(horizontal = SpotlightDimens.HomeScreenDrawerHeaderPadding)
            )

            ModalBottomSheetOption(
                icon = SpotlightIcons.Download,
                title = "Share",
                modifier = Modifier.padding(horizontal = SpotlightDimens.HomeScreenDrawerHeaderPadding)
            )
        }
    }
}

@Composable
fun ModalBottomSheetOption(
    icon: Int,
    title: String,
    modifier: Modifier = Modifier,
    withActionIcon: Boolean = false,
) {
    Box(
        modifier = modifier
            .height(SpotlightDimens.ModalBottomSheetOptionHeight)
            .fillMaxWidth(),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "",
            modifier = Modifier
                .size(SpotlightDimens.ModalBottomSheetOptionIconSize)
                .align(Alignment.CenterStart),
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = title,
            style = SpotlightTextStyle.Text14W400,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            modifier = Modifier
                .padding(horizontal = SpotlightDimens.ModalBottomSheetOptionTextPadding + SpotlightDimens.ModalBottomSheetOptionIconSize)
                .align(Alignment.CenterStart),
        )
        if (withActionIcon) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .size(SpotlightDimens.ModalBottomSheetOptionIconSize)
                    .align(Alignment.CenterEnd),
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}