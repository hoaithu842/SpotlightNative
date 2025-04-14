package io.github.hoaithu842.spotlight.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.github.hoaithu842.spotlight.presentation.screen.PremiumScreen
import kotlinx.serialization.Serializable

@Serializable
data object PremiumRoute

fun NavController.navigateToPremiumScreen(navOptions: NavOptions? = null) = navigate(route = PremiumRoute, navOptions = navOptions)

fun NavGraphBuilder.premiumScreen() {
    composable<PremiumRoute> {
        PremiumScreen()
    }
}
