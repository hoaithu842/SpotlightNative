package io.github.hoaithu842.spotlight.ui.designsystem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.hoaithu842.spotlight.R
import io.github.hoaithu842.spotlight.domain.model.SongDetails
import io.github.hoaithu842.spotlight.extension.noRippleClickable
import io.github.hoaithu842.spotlight.extension.singleClickable
import io.github.hoaithu842.spotlight.presentation.component.CircularCover
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens.TopAppBarHorizontalPadding
import io.github.hoaithu842.spotlight.ui.theme.MinimizedPlayerBackground
import io.github.hoaithu842.spotlight.ui.theme.NavigationGray
import io.github.hoaithu842.spotlight.ui.theme.ProgressIndicatorColor
import io.github.hoaithu842.spotlight.ui.theme.ProgressIndicatorTrackColor
import io.github.hoaithu842.spotlight.ui.theme.SpotlightTheme
import io.github.hoaithu842.spotlight.ui.theme.TopAppBarGray

enum class HomeScreenTab {
    All,
    Music,
    Podcasts,
}

enum class FilterCategory {
    None,
    Playlists,
    Podcasts,
    Albums,
    Artists,
}

@Composable
fun HomeTopAppBar(
    onAvatarClick: () -> Unit,
    currentTab: HomeScreenTab,
    onTabClick: (HomeScreenTab) -> Unit,
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
) {
    Row(
        modifier =
            modifier
                .fillMaxSize()
                .padding(bottom = TopAppBarHorizontalPadding * 2),
        verticalAlignment = Alignment.Bottom,
    ) {
        Row(
            modifier =
                Modifier
                    .wrapContentSize()
                    .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (avatarUrl != null) {
                CircularCover(
                    imageUrl = avatarUrl,
                    modifier =
                        Modifier
                            .padding(
                                start = SpotlightDimens.TopAppBarIconHorizontalPadding * 2,
                                end = SpotlightDimens.TopAppBarIconHorizontalPadding,
                            )
                            .size(SpotlightDimens.TopAppBarIconSize)
                            .clickable { onAvatarClick() },
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .padding(
                                start = SpotlightDimens.TopAppBarIconHorizontalPadding * 2,
                                end = SpotlightDimens.TopAppBarIconHorizontalPadding,
                            )
                            .size(SpotlightDimens.TopAppBarIconSize)
                            .clip(shape = CircleShape)
                            .clickable { onAvatarClick() },
                )
            }
            HomeTopAppBarOption(
                title = stringResource(id = R.string.all),
                selected = currentTab == HomeScreenTab.All,
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
                onOptionClick = {
                    onTabClick(HomeScreenTab.All)
                },
            )
            HomeTopAppBarOption(
                title = stringResource(id = R.string.music),
                selected = currentTab == HomeScreenTab.Music,
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
                onOptionClick = {
                    onTabClick(HomeScreenTab.Music)
                },
            )
            HomeTopAppBarOption(
                title = stringResource(id = R.string.podcasts),
                selected = currentTab == HomeScreenTab.Podcasts,
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
                onOptionClick = {
                    onTabClick(HomeScreenTab.Podcasts)
                },
            )
        }
    }
}

@Composable
fun HomeTopAppBarOption(
    title: String,
    selected: Boolean,
    onOptionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .wrapContentWidth()
                .height(SpotlightDimens.TopAppBarOptionHeight)
                .clip(shape = CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                .padding(horizontal = SpotlightDimens.TopAppBarOptionPadding)
                .noRippleClickable {
                    onOptionClick()
                },
    ) {
        Text(
            text = title,
            style = SpotlightTextStyle.Text11W400,
            color = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun LibraryTopAppBar(
    onAvatarClick: () -> Unit,
    onNavigateToSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(bottom = TopAppBarHorizontalPadding * 2),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier =
                Modifier
                    .padding(horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding * 2)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row {
                if (avatarUrl != null) {
                    CircularCover(
                        imageUrl = avatarUrl,
                        modifier =
                            Modifier
                                .padding(end = SpotlightDimens.TopAppBarIconHorizontalPadding)
                                .size(SpotlightDimens.TopAppBarIconSize)
                                .clickable { onAvatarClick() },
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        modifier =
                            Modifier
                                .padding(end = SpotlightDimens.TopAppBarIconHorizontalPadding)
                                .size(SpotlightDimens.TopAppBarIconSize)
                                .clip(shape = CircleShape)
                                .clickable { onAvatarClick() },
                    )
                }
                Text(
                    text = stringResource(R.string.library),
                    style = SpotlightTextStyle.Text22W700,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Row {
                Icon(
                    painter = painterResource(SpotlightIcons.Search),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .padding(horizontal = TopAppBarHorizontalPadding)
                            .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                            .padding(1.dp)
                            .noRippleClickable {
                                onNavigateToSearchClick()
                            },
                    tint = MaterialTheme.colorScheme.onBackground,
                )
                Icon(
                    painter = painterResource(SpotlightIcons.Add_2),
                    contentDescription = "",
                    modifier = Modifier.size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize),
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
        LibraryFiltering(
            modifier = Modifier.padding(horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding * 2),
        )
    }
}

@Composable
fun LibrarySearchTopAppBar(
    searchQuery: String,
    onCancelClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    bottom = TopAppBarHorizontalPadding * 2,
                    start = SpotlightDimens.TopAppBarIconHorizontalPadding,
                    end = SpotlightDimens.TopAppBarIconHorizontalPadding,
                ),
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        val onSearchExplicitlyTriggered = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(SpotlightDimens.LibraryTextFieldHeight)
                    .padding(end = SpotlightDimens.LibrarySearchButtonWidth)
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .background(TopAppBarGray)
                    .align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding)
                        .size(SpotlightDimens.LibrarySearchIconSize)
                        .noRippleClickable { onSearchExplicitlyTriggered() },
                imageVector = ImageVector.vectorResource(SpotlightIcons.Search),
                tint = NavigationGray,
                contentDescription = "",
            )

            BasicTextField(
                value = searchQuery,
                textStyle = SpotlightTextStyle.Text13W600.copy(color = MaterialTheme.colorScheme.onBackground),
                onValueChange = {
                    if ("\n" !in it) onSearchQueryChanged(it)
                },
                modifier =
                    Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                maxLines = 1,
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search) + " " + stringResource(R.string.library),
                            style = SpotlightTextStyle.Text13W600,
                            color = NavigationGray,
                        )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchExplicitlyTriggered() }),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            )
        }
        Row(
            modifier =
                Modifier
                    .height(SpotlightDimens.LibraryTextFieldHeight)
                    .width(SpotlightDimens.LibrarySearchButtonWidth)
                    .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.cancel),
                style = SpotlightTextStyle.Text11W600,
                color = NavigationGray,
                modifier = Modifier.singleClickable { onCancelClick() },
            )
        }
    }
}

@Composable
fun SearchResultTopAppBar(
    searchQuery: String,
    onCancelClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    bottom = TopAppBarHorizontalPadding * 2,
                    start = SpotlightDimens.TopAppBarIconHorizontalPadding,
                    end = SpotlightDimens.TopAppBarIconHorizontalPadding,
                ),
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        val onSearchExplicitlyTriggered = {
            keyboardController?.hide()
            focusManager.clearFocus()
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(SpotlightDimens.LibraryTextFieldHeight)
                    .padding(end = SpotlightDimens.LibrarySearchButtonWidth)
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .background(TopAppBarGray)
                    .align(Alignment.BottomStart),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier =
                    Modifier
                        .padding(horizontal = SpotlightDimens.TopAppBarIconHorizontalPadding)
                        .size(SpotlightDimens.LibrarySearchIconSize)
                        .noRippleClickable { onSearchExplicitlyTriggered() },
                imageVector = ImageVector.vectorResource(SpotlightIcons.Search),
                tint = NavigationGray,
                contentDescription = "",
            )

            BasicTextField(
                value = searchQuery,
// textStyle = WordbookSearchTextStyle.copy(color = MaterialTheme.colorScheme.inverseSurface),
                onValueChange = {
                    if ("\n" !in it) onSearchQueryChanged(it)
                },
                modifier =
                    Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                maxLines = 1,
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.what_do_you_want_to_listen),
                            style = SpotlightTextStyle.Text13W600,
                            color = NavigationGray,
                        )
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchExplicitlyTriggered() }),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            )
        }
        Row(
            modifier =
                Modifier
                    .height(SpotlightDimens.LibraryTextFieldHeight)
                    .width(SpotlightDimens.LibrarySearchButtonWidth)
                    .align(Alignment.BottomEnd),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.cancel),
                style = SpotlightTextStyle.Text11W600,
                color = NavigationGray,
                modifier = Modifier.singleClickable { onCancelClick() },
            )
        }
    }
}

@Preview
@Composable
fun LibraryFiltering(modifier: Modifier = Modifier) {
    var isFiltered by remember { mutableStateOf(false) }
    var filterCategory by remember { mutableStateOf(FilterCategory.None) }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.TopAppBarHeight),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedVisibility(
            visible = isFiltered,
        ) {
            Icon(
                painter = painterResource(SpotlightIcons.Add),
                contentDescription = "",
                modifier =
                    Modifier
                        .size(SpotlightDimens.TopAppBarOptionHeight)
                        .noRippleClickable {
                            isFiltered = false
                            filterCategory = FilterCategory.None
                        },
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }

        AnimatedVisibility(
            visible = !isFiltered || filterCategory == FilterCategory.Playlists,
        ) {
            LibraryTopAppBarOption(
                title = "Playlists",
                selected = filterCategory == FilterCategory.Playlists,
                onOptionClick = {
                    if (isFiltered) {
                        isFiltered = false
                        filterCategory = FilterCategory.None
                    } else {
                        isFiltered = true
                        filterCategory = FilterCategory.Playlists
                    }
                },
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
            )
        }

        AnimatedVisibility(
            visible = !isFiltered || filterCategory == FilterCategory.Podcasts,
        ) {
            LibraryTopAppBarOption(
                title = "Podcasts",
                selected = filterCategory == FilterCategory.Podcasts,
                onOptionClick = {
                    if (isFiltered) {
                        isFiltered = false
                        filterCategory = FilterCategory.None
                    } else {
                        isFiltered = true
                        filterCategory = FilterCategory.Podcasts
                    }
                },
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
            )
        }

        AnimatedVisibility(
            visible = !isFiltered || filterCategory == FilterCategory.Albums,
        ) {
            LibraryTopAppBarOption(
                title = "Albums",
                selected = filterCategory == FilterCategory.Albums,
                onOptionClick = {
                    if (isFiltered) {
                        isFiltered = false
                        filterCategory = FilterCategory.None
                    } else {
                        isFiltered = true
                        filterCategory = FilterCategory.Albums
                    }
                },
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
            )
        }
        AnimatedVisibility(
            visible = !isFiltered || filterCategory == FilterCategory.Artists,
        ) {
            LibraryTopAppBarOption(
                title = "Artists",
                selected = filterCategory == FilterCategory.Artists,
                onOptionClick = {
                    if (isFiltered) {
                        isFiltered = false
                        filterCategory = FilterCategory.None
                    } else {
                        isFiltered = true
                        filterCategory = FilterCategory.Artists
                    }
                },
                modifier = Modifier.padding(horizontal = TopAppBarHorizontalPadding),
            )
        }
    }
}

@Composable
fun LibraryTopAppBarOption(
    title: String,
    selected: Boolean,
    onOptionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .wrapContentWidth()
                .height(SpotlightDimens.TopAppBarOptionHeight)
                .clip(shape = CircleShape)
                .background(if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                .padding(horizontal = SpotlightDimens.TopAppBarOptionPadding)
                .noRippleClickable {
                    onOptionClick()
                },
    ) {
        Text(
            text = title,
            style = SpotlightTextStyle.Text11W400,
            color = if (selected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onBackground,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onAvatarClick: () -> Unit,
    avatarUrl: String? = null,
) {
    TopAppBar(
        navigationIcon = {
            Box(
                modifier = Modifier.size(SpotlightDimens.TopAppBarHeight),
            ) {
                if (avatarUrl != null) {
                    CircularCover(
                        imageUrl = avatarUrl,
                        modifier =
                            Modifier
                                .padding(
                                    start = SpotlightDimens.SearchTopAppBarPadding + 2.dp,
                                    bottom = TopAppBarHorizontalPadding * 2 + 2.dp,
                                )
                                .size(SpotlightDimens.TopAppBarIconSize)
                                .clickable { onAvatarClick() }
                                .align(Alignment.BottomStart),
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        modifier =
                            Modifier
                                .padding(
                                    start = SpotlightDimens.SearchTopAppBarPadding + 2.dp,
                                    bottom = TopAppBarHorizontalPadding * 2 + 2.dp,
                                )
                                .size(SpotlightDimens.TopAppBarIconSize)
                                .clip(shape = CircleShape)
                                .clickable { onAvatarClick() }
                                .align(Alignment.BottomStart),
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(R.string.search),
                style = SpotlightTextStyle.Text22W700,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = TopAppBarHorizontalPadding),
            )
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                scrolledContainerColor = Color.Transparent,
            ),
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun FullsizePlayerTopAppBar(
    artists: String,
    onMinimizeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.FullsizePlayerTopAppBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            painter = painterResource(SpotlightIcons.Down),
            contentDescription = "",
            modifier =
                Modifier
                    .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                    .noRippleClickable {
                        onMinimizeClick()
                    },
            tint = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = artists,
            style = SpotlightTextStyle.Text11W400,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = SpotlightDimens.FullsizePlayerTopAppBarPadding),
        )
        Icon(
            painter = painterResource(SpotlightIcons.More),
            contentDescription = "",
            modifier =
                Modifier
                    .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                    .noRippleClickable {},
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun PlayerControllerTopAppBar(
    isPlaying: Boolean,
    song: SongDetails,
    currentPosition: Long,
    duration: Long,
    onPlayerClick: () -> Unit,
    onMainFunctionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(SpotlightDimens.MinimizedPlayerHeight)
                .clip(shape = RoundedCornerShape(size = 12.dp))
                .background(MinimizedPlayerBackground)
                .noRippleClickable {
                    onPlayerClick()
                }
                .padding(bottom = 2.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = SpotlightDimens.MinimizedPlayerThumbnailPaddingStart),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(end = SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize.times(2))
                        .height(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                        .align(Alignment.CenterStart),
            ) {
                Text(
                    text = song.title,
                    style = SpotlightTextStyle.Text11W400,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .basicMarquee(),
                )
                Text(
                    text = song.artists,
                    style = SpotlightTextStyle.Text11W400,
                    overflow = TextOverflow.Ellipsis,
                    color = NavigationGray,
                    maxLines = 1,
                )
            }

            Icon(
                painter = painterResource(if (isPlaying) SpotlightIcons.Pause else SpotlightIcons.Play),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier =
                    Modifier
                        .size(SpotlightDimens.HomeScreenDrawerHeaderOptionIconSize)
                        .align(Alignment.CenterEnd)
                        .noRippleClickable {
                            onMainFunctionClick()
                        },
            )
        }

        LinearProgressIndicator(
            progress = { if (duration.toInt() == 0) 0f else (currentPosition * 1.0 / duration).toFloat() },
            modifier =
                Modifier
                    .padding(horizontal = SpotlightDimens.MinimizedPlayerProgressIndicatorPadding)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            color = ProgressIndicatorTrackColor,
            trackColor = ProgressIndicatorColor,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Preview
@Composable
fun TopAppBarPreview() {
    SpotlightTheme {
        Column {
            HomeTopAppBar(
                onAvatarClick = {},
                currentTab = HomeScreenTab.All,
                onTabClick = {},
                modifier = Modifier.padding(vertical = 20.dp),
            )

            FullsizePlayerTopAppBar(
                artists = "Preview",
                onMinimizeClick = {},
                modifier = Modifier.padding(vertical = 20.dp),
            )

            PlayerControllerTopAppBar(
                song =
                    SongDetails(
                        title = "Preview",
                        artists = "Preview",
                    ),
                isPlaying = true,
                currentPosition = 0,
                duration = 232155,
                onPlayerClick = {},
                onMainFunctionClick = {},
                modifier = Modifier.padding(vertical = 20.dp),
            )
        }
    }
}
