package io.github.hoaithu842.spotlight_native

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.Alignment
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
import io.github.hoaithu842.spotlight_native.navigation.SpotlightNavHost
import io.github.hoaithu842.spotlight_native.navigation.TopLevelDestination
import io.github.hoaithu842.spotlight_native.navigation.navigateToTopLevelDestination
import io.github.hoaithu842.spotlight_native.presentation.screen.PlayerView
import io.github.hoaithu842.spotlight_native.ui.designsystem.CustomDrawerState
import io.github.hoaithu842.spotlight_native.ui.designsystem.HomeScreenDrawer
import io.github.hoaithu842.spotlight_native.ui.designsystem.NoNetworkDialog
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightNavigationBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightNavigationBarItem
import io.github.hoaithu842.spotlight_native.ui.designsystem.isOpened
import io.github.hoaithu842.spotlight_native.ui.designsystem.opposite
import kotlin.math.roundToInt
import kotlin.reflect.KClass

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotlightContent(
    isOffline: Boolean,
) {
    var isNavBarDisplaying by rememberSaveable { mutableStateOf(true) }
    val density = LocalDensity.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val navController = rememberNavController()
    var currentDestination: NavDestination? =
        navController.currentBackStackEntryAsState().value?.destination
    var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
    val configuration = LocalConfiguration.current
    val newDensity = LocalDensity.current.density
    val screenWidth = remember {
        derivedStateOf { (configuration.screenWidthDp * newDensity).roundToInt() }
    }
    val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
        label = "",
    )
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
        if (scaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
            isNavBarDisplaying = true
        }
    }
    BackHandler(enabled = drawerState.isOpened()) {
        drawerState = CustomDrawerState.Closed
    }
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .fillMaxSize()
    ) {
        HomeScreenDrawer()
        Scaffold(
            modifier = Modifier
                .offset(x = animatedOffset)
                .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                    drawerState = CustomDrawerState.Closed
                },
            bottomBar = {
                AnimatedVisibility(
                    visible = isNavBarDisplaying,
                    enter = slideInVertically {
                        with(density) { -40.dp.roundToPx() }
                    } + expandVertically(
                        expandFrom = Alignment.Top
                    ) + fadeIn(
                        initialAlpha = 0.3f
                    ),
                    exit = slideOutVertically() + shrinkVertically() + fadeOut()
                ) {
                    SpotlightNavigationBar {
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
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .fillMaxSize()
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
                            navBarDisplayingChange = { isNavBarDisplaying = it },
                        )
                    },
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    sheetContainerColor = Color.Transparent,
                    sheetMaxWidth = configuration.screenWidthDp.dp,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SpotlightNavHost(
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
    }
}