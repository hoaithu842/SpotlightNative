package io.github.hoaithu842.spotlight_native.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.hoaithu842.spotlight_native.presentation.screen.RecommendationScreen
import kotlinx.serialization.Serializable

@Serializable
data object RecommendationRoute

fun NavController.navigateToRecommendationScreen(navOptions: NavOptions? = null) =
    navigate(route = RecommendationRoute, navOptions = navOptions)

fun NavGraphBuilder.recommendationScreen(
    onBackClick: () -> Unit,
) {
    composable<RecommendationRoute> {
        RecommendationScreen(
            imageUrl = "https://thantrieu.com/resources/arts/1078245023.webp", // replace with id
            onBackClick = onBackClick,
        )
    }
}