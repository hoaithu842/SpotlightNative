package io.github.hoaithu842.spotlight_native

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import io.github.hoaithu842.spotlight_native.navigation.SpotlightNavHost
import io.github.hoaithu842.spotlight_native.navigation.TopLevelDestination
import io.github.hoaithu842.spotlight_native.navigation.navigateToHomeScreen
import io.github.hoaithu842.spotlight_native.navigation.navigateToLibraryScreen
import io.github.hoaithu842.spotlight_native.navigation.navigateToPremiumScreen
import io.github.hoaithu842.spotlight_native.navigation.navigateToSearchScreen
import io.github.hoaithu842.spotlight_native.ui.components.HomeScreenDrawer
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightNavigationBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightNavigationBarItem
import io.github.hoaithu842.spotlight_native.ui.theme.BlurGray
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme
import kotlinx.coroutines.launch

fun navigateToTopLevelDestination(
    navController: NavHostController,
    topLevelDestination: TopLevelDestination,
) {
    val topLevelNavOptions = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    when (topLevelDestination) {
        TopLevelDestination.HOME -> navController.navigateToHomeScreen(
            topLevelNavOptions
        )

        TopLevelDestination.SEARCH -> navController.navigateToSearchScreen(
            topLevelNavOptions
        )

        TopLevelDestination.LIBRARY -> navController.navigateToLibraryScreen(
            topLevelNavOptions
        )

        TopLevelDestination.PREMIUM -> navController.navigateToPremiumScreen(
            topLevelNavOptions
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var currentDestination by remember { mutableStateOf(TopLevelDestination.HOME) }


            val coroutineScope = rememberCoroutineScope()
            val pagerState = rememberPagerState(
                initialPage = 1,
                pageCount = {
                    2
                },
            )
            var currentScrollState by remember { mutableStateOf(false) }

            LaunchedEffect(pagerState.currentPage) {
                currentScrollState = pagerState.currentPage == 0
            }

            SpotlightTheme {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = if (pagerState.currentPage == 0) PaddingValues(end = 80.dp) else PaddingValues(
                        end = 0.dp
                    ),
                    userScrollEnabled = currentScrollState,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                ) { page ->
                    if (page == 0) {
                        HomeScreenDrawer(
//                            onSwipeLeft = {
//                                coroutineScope.launch {
//                                    pagerState.animateScrollToPage(1)
//                                }
//                            },
//                            modifier = Modifier.padding(end = 70.dp)
                        )
                    } else {
                        Box(
                            Modifier.fillMaxSize()
                        ) {
                            Scaffold(
                                modifier = Modifier.fillMaxSize(),
                                bottomBar = {
                                    SpotlightNavigationBar {
                                        TopLevelDestination.entries.forEach { destination ->
                                            SpotlightNavigationBarItem(
                                                title = stringResource(id = destination.title),
                                                selected = currentDestination == destination,
                                                icon = destination.unselectedIcon,
                                                selectedIcon = destination.selectedIcon,
                                                onClick = {
                                                    if (currentDestination != destination) {
                                                        navigateToTopLevelDestination(
                                                            navController = navController,
                                                            topLevelDestination = destination,
                                                        )
                                                        currentDestination = destination
                                                    }
                                                },
                                            )
                                        }
                                    }
                                },
                            ) { innerPadding ->
                                Column(
                                    modifier = Modifier.padding(innerPadding),
                                ) {
                                    SpotlightNavHost(
                                        navHostController = navController,
                                        onAvatarClick = {
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(0)
                                            }
                                        },
                                    )
                                }
                            }
                            if (pagerState.currentPage == 0) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color = BlurGray)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}