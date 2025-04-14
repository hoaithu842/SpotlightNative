package io.github.hoaithu842.spotlight.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions

fun navigateToTopLevelDestination(
    navController: NavHostController,
    topLevelDestination: TopLevelDestination,
) {
    val topLevelNavOptions =
        navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    when (topLevelDestination) {
        TopLevelDestination.HOME ->
            navController.navigateToHome(
                topLevelNavOptions,
            )

        TopLevelDestination.SEARCH ->
            navController.navigateToSearch(
                topLevelNavOptions,
            )

        TopLevelDestination.LIBRARY ->
            navController.navigateToLibrary(
                topLevelNavOptions,
            )

        TopLevelDestination.PREMIUM ->
            navController.navigateToPremiumScreen(
                topLevelNavOptions,
            )
    }
}

@Composable
fun SpotlightNavHost(
    navHostController: NavHostController,
    onAvatarClick: () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeGraph,
    ) {
        homeGraph(
            onAvatarClick = onAvatarClick,
            onArtistClick = navHostController::navigateToArtistScreen,
            onRecommendedPlaylistClick = navHostController::navigateToRecommendationScreen,
            onBackClick = navHostController::popBackStack,
        )
        searchGraph(
            onAvatarClick = onAvatarClick,
            onCancelClick = navHostController::popBackStack,
            onNavigateToSearchClick = {
                navHostController.navigateToSearchResultScreen(
                    navOptions =
                        navOptions {
                            launchSingleTop = true
                        },
                )
            },
        )
        libraryGraph(
            onAvatarClick = onAvatarClick,
            onCancelClick = navHostController::popBackStack,
            onNavigateToSearchClick = {
                navHostController.navigateToLibrarySearchScreen(
                    navOptions =
                        navOptions {
                            launchSingleTop = true
                        },
                )
            },
        )
        premiumScreen()
    }
}
