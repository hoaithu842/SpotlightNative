package io.github.hoaithu842.spotlight_native.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import io.github.hoaithu842.spotlight_native.extension.shimmerLoadingAnimation

@Composable
fun Cover(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var isLoading by remember { mutableStateOf(true) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .listener(onSuccess = { _, _ ->
                isLoading = false
            })
            .build()
    )

    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier
            .fillMaxSize()
            .shimmerLoadingAnimation(
                isLoadingCompleted = !isLoading,
                isLightModeActive = !isSystemInDarkTheme(),
            ),
        contentScale = contentScale,
    )
}

@Composable
fun CircularCover(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var isLoading by remember { mutableStateOf(true) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .listener(onSuccess = { _, _ ->
                isLoading = false
            })
            .build()
    )

    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape)
            .shimmerLoadingAnimation(
                isLoadingCompleted = !isLoading,
                isLightModeActive = !isSystemInDarkTheme(),
            ),
        contentScale = contentScale,
    )
}

@Composable
fun RoundedCornerCover(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    var isLoading by remember { mutableStateOf(true) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .listener(onSuccess = { _, _ ->
                isLoading = false
            })
            .build()
    )

    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .shimmerLoadingAnimation(
                isLoadingCompleted = !isLoading,
                isLightModeActive = !isSystemInDarkTheme(),
            ),
        contentScale = contentScale,
    )
}