package io.github.hoaithu842.spotlight

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.hoaithu842.spotlight.domain.model.UserProfile
import io.github.hoaithu842.spotlight.navigation.SpotlightNavHost
import io.github.hoaithu842.spotlight.navigation.TopLevelDestination
import io.github.hoaithu842.spotlight.navigation.navigateToTopLevelDestination
import io.github.hoaithu842.spotlight.presentation.screen.PlayerView
import io.github.hoaithu842.spotlight.ui.designsystem.CustomDrawerState
import io.github.hoaithu842.spotlight.ui.designsystem.HomeScreenDrawer
import io.github.hoaithu842.spotlight.ui.designsystem.NoNetworkDialog
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightNavigationBar
import io.github.hoaithu842.spotlight.ui.designsystem.SpotlightNavigationBarItem
import io.github.hoaithu842.spotlight.ui.designsystem.isOpened
import io.github.hoaithu842.spotlight.ui.designsystem.opposite
import kotlin.math.roundToInt
import kotlin.reflect.KClass

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotlightContent(
    loginLoading: Boolean,
    userProfile: UserProfile?,
    isOffline: Boolean,
    onAvatarClick: () -> Unit,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    var isNavBarDisplaying by rememberSaveable { mutableStateOf(true) }
    val scaffoldState = rememberBottomSheetScaffoldState()
    val navController = rememberNavController()
    val currentDestination: NavDestination? =
        navController.currentBackStackEntryAsState().value?.destination
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    val configuration = LocalConfiguration.current
    val newDensity = LocalDensity.current.density
    val screenWidth =
        remember {
            derivedStateOf { (configuration.screenWidthDp * newDensity).roundToInt() }
        }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 3.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "",
    )
    val snackbarHostState = remember { SnackbarHostState() }
    var currentYOffset by remember { mutableStateOf(0.dp) }
    var previousDelta: Float? = remember { null }

    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
            isNavBarDisplaying = true
        } else if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
            isNavBarDisplaying = false
        }
    }

    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }

    Box(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .fillMaxSize(),
    ) {
        HomeScreenDrawer(
            userProfile = userProfile,
            onProfileClick = onAvatarClick,
            onLoginClick = onLoginClick,
            onLogoutClick = onLogoutClick,
        )
        Scaffold(
            modifier =
            Modifier
                .offset(x = animatedOffset)
                .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                    drawerState = CustomDrawerState.Closed
                },
            bottomBar = {
                if (isNavBarDisplaying) {
                    SpotlightNavigationBar(
                        modifier = Modifier.offset(0.dp, currentYOffset),
                    ) {
                        TopLevelDestination.entries.forEach { destination ->
                            val selected =
                                currentDestination.isRouteInHierarchy(destination.route)
                            SpotlightNavigationBarItem(
                                title = stringResource(destination.title),
                                selected = selected,
                                icon = destination.unselectedIcon,
                                selectedIcon = destination.selectedIcon,
                                onClick = {
                                    navigateToTopLevelDestination(
                                        navController = navController,
                                        topLevelDestination = destination,
                                    )
                                },
                            )
                        }
                    }
                }
            },
        ) { innerPadding ->
            Box(
                modifier =
                Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .fillMaxSize(),
            ) {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = SpotlightDimens.MinimizedPlayerHeight,
                    sheetShape = RoundedCornerShape(0.dp),
                    sheetDragHandle = {},
                    sheetShadowElevation = 0.dp,
                    sheetContent = {
                        PlayerView(
                            scaffoldState = scaffoldState,
                            changeNavBarDisplay = {
                                isNavBarDisplaying = !isNavBarDisplaying
                                previousDelta = null
                            },
                            onScrollProvider = {
                                try {
                                    scaffoldState.bottomSheetState.requireOffset()
                                } catch (_: Exception) {
                                    0f
                                }
                            },
                            updateDelta = {
                                if (it in -5.1..-4.0) {
                                    if (previousDelta == null) {
                                        previousDelta = it
                                    } else {
                                        isNavBarDisplaying =
                                            if (it > previousDelta!!) {
                                                true
                                            } else {
                                                false
                                            }
                                        previousDelta = null
                                    }
                                }
                                currentYOffset =
                                    ((1 - it) * SpotlightDimens.NavigationBarHeight.value).dp
                            },
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    sheetContainerColor = Color.Transparent,
                    sheetMaxWidth = configuration.screenWidthDp.dp + 1.dp,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SpotlightNavHost(
                        userProfile = userProfile,
                        navHostController = navController,
                        onAvatarClick = { drawerState = drawerState.opposite() },
                    )
                }
            }
        }
        if (!isOffline) {
            NoNetworkDialog(
                title = "No internet",
                message = "Something went wrong! You're offline.",
                backgroundColor = MaterialTheme.colorScheme.surface,
                onDismissRequest = {},
            )
        }
        if (loginLoading) {
            NoNetworkDialog(
                title = "Loading",
                message = "In progress, please wait!",
                backgroundColor = MaterialTheme.colorScheme.surface,
                onDismissRequest = {},
            )
        }
    }
}
