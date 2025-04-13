package io.github.hoaithu842.spotlight_native.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.github.hoaithu842.spotlight_native.presentation.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeGraph

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(route = HomeGraph, navOptions = navOptions)

fun NavGraphBuilder.homeGraph(
    onAvatarClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    navigation<HomeGraph>(
        startDestination = HomeRoute,
    ) {
        homeScreen(
            onAvatarClick = onAvatarClick,
            onArtistClick = onArtistClick,
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
        )

        recommendationScreen(
            onBackClick = onBackClick,
        )

        artistScreen(
            onBackClick = onBackClick,
        )
    }
}

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeScreen(
    onAvatarClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (Int) -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            onAvatarClick = onAvatarClick,
            onArtistClick = onArtistClick,
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
        )
    }
}
