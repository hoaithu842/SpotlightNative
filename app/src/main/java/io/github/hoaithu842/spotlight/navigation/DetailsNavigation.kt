package io.github.hoaithu842.spotlight.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.hoaithu842.spotlight.presentation.screen.ArtistScreen
import io.github.hoaithu842.spotlight.presentation.screen.RecommendationScreen
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationRoute(val id: String)

fun NavController.navigateToRecommendationScreen(
    id: String,
    navOptions: NavOptions? = null,
) = navigate(route = RecommendationRoute(id), navOptions = navOptions)

fun NavGraphBuilder.recommendationScreen(onBackClick: () -> Unit) {
    composable<RecommendationRoute> { backStackEntry ->
        val recommendationRoute: RecommendationRoute = backStackEntry.toRoute()

        RecommendationScreen(
            id = recommendationRoute.id,
            imageUrl = "https://thantrieu.com/resources/arts/1078245023.webp",
            onBackClick = onBackClick,
        )
    }
}

@Serializable
data class ArtistRoute(val id: String)

fun NavController.navigateToArtistScreen(
    id: String,
    navOptions: NavOptions? = null,
) = navigate(route = ArtistRoute(id), navOptions = navOptions)

fun NavGraphBuilder.artistScreen(onBackClick: () -> Unit) {
    composable<ArtistRoute> { backStackEntry ->
        val artistRoute: ArtistRoute = backStackEntry.toRoute()

        ArtistScreen(
            id = artistRoute.id,
            onBackClick = onBackClick,
        )
    }
}
