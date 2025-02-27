package io.github.hoaithu842.spotlight_native.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.hoaithu842.spotlight_native.presentation.screen.RecommendationScreen
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationRoute(val id: Int)

fun NavController.navigateToRecommendationScreen(id: Int, navOptions: NavOptions? = null) =
    navigate(route = RecommendationRoute(id), navOptions = navOptions)

fun NavGraphBuilder.recommendationScreen(
    onBackClick: () -> Unit,
) {
    composable<RecommendationRoute> { backStackEntry ->
        val recommendationRoute: RecommendationRoute = backStackEntry.toRoute()

        RecommendationScreen(
            id = recommendationRoute.id,
            imageUrl = "https://thantrieu.com/resources/arts/1078245023.webp", // replace with id
            onBackClick = onBackClick,
        )
    }
}