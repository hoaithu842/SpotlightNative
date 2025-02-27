package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenDrawerHeader
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.theme.NavigationGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(size = 6.dp)
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            HomeScreenDrawerHeader(
                username = "Hoai Thu",
                onAvatarClick = {},
            )

            HorizontalDivider(
                thickness = 0.15.dp,
                color = NavigationGray,
                modifier = Modifier
                    .padding(vertical = SpotlightDimens.HomeScreenDrawerHeaderVerticalPadding)
                    .fillMaxWidth(),
            )
        }
    }
}