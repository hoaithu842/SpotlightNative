package io.github.hoaithu842.spotlight.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import io.github.hoaithu842.spotlight.domain.model.UserProfile
import io.github.hoaithu842.spotlight.presentation.screen.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeGraph

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(route = HomeGraph, navOptions = navOptions)

fun NavGraphBuilder.homeGraph(
    userProfile: UserProfile?,
    onAvatarClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    navigation<HomeGraph>(
        startDestination = HomeRoute,
    ) {
        homeScreen(
            userProfile = userProfile,
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
    userProfile: UserProfile?,
    onAvatarClick: () -> Unit,
    onArtistClick: (String) -> Unit,
    onRecommendedPlaylistClick: (String) -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            userProfile = userProfile,
            onAvatarClick = onAvatarClick,
            onArtistClick = onArtistClick,
            onRecommendedPlaylistClick = onRecommendedPlaylistClick,
        )
    }
}
