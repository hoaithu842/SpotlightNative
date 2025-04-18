package io.github.hoaithu842.spotlight.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.hoaithu842.spotlight.R
import io.github.hoaithu842.spotlight.domain.model.Image
import io.github.hoaithu842.spotlight.domain.model.Song
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.extension.singleClickable
import io.github.hoaithu842.spotlight.extension.toColor
import io.github.hoaithu842.spotlight.presentation.component.Cover
import io.github.hoaithu842.spotlight.presentation.component.SongItem
import io.github.hoaithu842.spotlight.presentation.viewmodel.RecommendationUiState
import io.github.hoaithu842.spotlight.presentation.viewmodel.RecommendationViewModel
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightIcons
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightTextStyle
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray
import kotlinx.coroutines.launch

@Composable
fun RecommendationScreen(
    id: String,
    imageUrl: String,
    onBackClick: () -> Unit,
    maxImageSize: Dp = SpotlightDimens.RecommendationScreenThumbnailMaxSize,
    minImageSize: Dp = SpotlightDimens.FullsizePlayerTopAppBarHeight,
    minAlpha: Float = 0f,
    maxAlpha: Float = 1f,
    viewModel: RecommendationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.recommendationUiState.collectAsState()

    var currentImageSize by remember { mutableStateOf(maxImageSize) }
    var currentImageAlpha by remember { mutableFloatStateOf(maxAlpha) }
    var imageScale by remember { mutableFloatStateOf(1f) }
    val scope = rememberCoroutineScope()

    val nestedScrollConnection =
        remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource,
                ): Offset {
                    // Calculate the change in image size based on scroll delta
                    val delta = available.y
                    val newImageSize = currentImageSize + delta.dp
                    val previousImageSize = currentImageSize

                    // Constrain the image size within the allowed bounds
                    currentImageSize = newImageSize.coerceIn(minImageSize, maxImageSize)
                    val consumed = currentImageSize - previousImageSize

                    // Calculate the scale for the image
                    imageScale = currentImageSize / maxImageSize

                    val sizeDelta =
                        (currentImageSize - minImageSize) / (maxImageSize - minImageSize)

                    currentImageAlpha = sizeDelta.coerceIn(minAlpha, maxAlpha)

                    // Return the consumed scroll amount
                    return Offset(0f, consumed.value)
                }
            }
        }

    when (uiState) {
        RecommendationUiState.Error -> Text(text = "Error")
        RecommendationUiState.Loading -> Text(text = "Loading")
        is RecommendationUiState.Success -> {
            val state = (uiState as RecommendationUiState.Success)
            val dynamicColor =
                (
                    state.album.color?.toColor()
                        ?: MaterialTheme.colorScheme.onSurface
                ).copy(alpha = imageScale.coerceIn(0f, 1f))
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colorStops =
                                    arrayOf(
                                        0.0f to dynamicColor,
                                        1f to Color.Black,
                                    ),
                            ),
                    )
                    .nestedScroll(nestedScrollConnection)
                    .statusBarsPadding()
                    .padding(top = 10.dp),
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .offset {
                            IntOffset(0, currentImageSize.roundToPx())
                        },
                ) {
                    item {
                        RecommendationDetails(
                            description = state.album.artists ?: "",
                            modifier = Modifier.padding(vertical = SpotlightDimens.TopAppBarHorizontalPadding * 2),
                        )
                    }
                    item {
                        RecommendationScreenFunctionBar(
                            onPlayClick = {
                                scope.launch {
                                    viewModel.playAlbum()
                                }
                            },
                            modifier = Modifier.padding(vertical = SpotlightDimens.TopAppBarHorizontalPadding * 2),
                        )
                    }
                    // Placeholder list items
                    items(state.album.items?.size ?: 0, key = { it }) {
                        state.album.items?.get(it)?.let { item ->
                            SongItem(
                                song = item.song ?: Song(),
                                cover = item.image ?: Image(),
                                modifier = Modifier.padding(vertical = SpotlightDimens.TopAppBarHorizontalPadding),
                            )
                        }
                    }
                }

                Cover(
                    imageUrl = state.album.image?.url ?: "",
                    modifier =
                        Modifier
                            .size(maxImageSize)
                            .align(Alignment.TopCenter)
                            .graphicsLayer {
                                scaleX = imageScale
                                scaleY = imageScale
                                // Center the image vertically as it scales
                                translationY = -(maxImageSize.toPx() - currentImageSize.toPx()) / 2f
                            }
                            .alpha(currentImageAlpha),
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(SpotlightDimens.FullsizePlayerTopAppBarHeight)
                            .align(Alignment.TopStart),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier =
                            Modifier
                                .padding(
                                    start = SpotlightDimens.TopAppBarIconHorizontalPadding * 2,
                                    end = SpotlightDimens.TopAppBarIconHorizontalPadding,
                                )
                                .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                                .singleClickable { onBackClick() },
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = "",
                    )
                    AnimatedVisibility(
                        visible = currentImageAlpha == 0f,
                        enter =
                            slideIn(
                                initialOffset = { IntOffset(0, (it.height)) / 2f },
                                animationSpec =
                                    spring(
                                        stiffness = Spring.StiffnessMediumLow,
                                        visibilityThreshold = IntOffset.VisibilityThreshold,
                                    ),
                            ),
                    ) {
                        Text(
                            text = state.album.title ?: "",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = SpotlightTextStyle.Text16W600,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier
                                .padding(
                                    end = SpotlightDimens.TopAppBarIconHorizontalPadding * 2,
                                    start = SpotlightDimens.TopAppBarIconHorizontalPadding,
                                )
                                .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
                    )
                }
            }
        }
    }
}

@Composable
fun RecommendationDetails(
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = description,
            style = SpotlightTextStyle.Text14W400,
            color = NavigationGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = SpotlightDimens.TopAppBarHorizontalPadding * 2),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
            Text(
                text = "Made for you",
                style = SpotlightTextStyle.Text14W400,
                color = NavigationGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.TopAppBarHorizontalPadding * 2)
                        .fillMaxWidth(),
            )
        }
        Text(
            text = "3h25m",
            style = SpotlightTextStyle.Text14W400,
            color = NavigationGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun RecommendationScreenFunctionBar(
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.FullsizePlayerTopAppBarHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FirstRecommendationPreview(
                modifier =
                    Modifier
                        .height(SpotlightDimens.FullsizePlayerTopAppBarHeight)
                        .width(SpotlightDimens.FullsizePlayerTopAppBarHeight * 3 / 4),
            )
            Icon(
                painter = painterResource(SpotlightIcons.Add),
                contentDescription = "",
                tint = NavigationGray,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationPadding * 3)
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize + 4.dp),
            )
            Icon(
                painter = painterResource(SpotlightIcons.Download),
                contentDescription = "",
                tint = NavigationGray,
                modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "",
                tint = NavigationGray,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationPadding * 3)
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(SpotlightIcons.Shuffle),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.RecommendationPadding * 3)
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
            )
            Icon(
                painter = painterResource(SpotlightIcons.Play),
                contentDescription = "",
                tint = MinimizedPlayerBackground,
                modifier =
                    Modifier
                        .size(SpotlightDimens.FullsizePlayerTopAppBarHeight)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding((SpotlightDimens.FullsizePlayerTopAppBarHeight - SpotlightDimens.PlayerControllerMediumIconSize) / 2)
                        .noRippleClickable(onPlayClick),
            )
        }
    }
}

@Composable
fun FirstRecommendationPreview(modifier: Modifier = Modifier) {
    CardWithAnimatedBorder(modifier = modifier) {
        Cover(
            imageUrl = "https://thantrieu.com/resources/arts/1078245010.webp",
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {},
    borderColors: List<Color> = emptyList(),
    content: @Composable () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(1500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "",
        )

    val brush =
        if (borderColors.isNotEmpty()) {
            Brush.sweepGradient(borderColors)
        } else {
            Brush.sweepGradient(listOf(Color.Gray, Color.White))
        }

    Surface(
        modifier = modifier.clickable { onCardClick() },
        shape = RoundedCornerShape(6.dp),
    ) {
        Surface(
            modifier =
                Modifier
                    .clipToBounds()
                    .fillMaxWidth()
                    .padding(1.dp)
                    .drawWithContent {
                        rotate(angle) {
                            drawCircle(
                                brush = brush,
                                radius = size.width,
                                blendMode = BlendMode.SrcIn,
                            )
                        }
                        drawContent()
                    },
            color = Color.Red,
            shape = RoundedCornerShape(6.dp),
        ) {
            Box(modifier = Modifier) { content() }
        }
    }
}
