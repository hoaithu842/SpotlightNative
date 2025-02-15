package io.github.hoaithu842.spotlight_native.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.github.hoaithu842.spotlight_native.R
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightIcons
import kotlin.reflect.KClass

enum class TopLevelDestination(
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    @StringRes val title: Int,
    val route: KClass<*>,
) {
    HOME(
        unselectedIcon = SpotlightIcons.Home,
        selectedIcon = SpotlightIcons.HomeSelected,
        title = R.string.home,
        route = HomeRoute::class,
    ),
    SEARCH(
        unselectedIcon = SpotlightIcons.Search,
        selectedIcon = SpotlightIcons.SearchSelected,
        title = R.string.search,
        route = SearchRoute::class,
    ),
    LIBRARY(
        unselectedIcon = SpotlightIcons.Library,
        selectedIcon = SpotlightIcons.LibrarySelected,
        title = R.string.library,
        route = LibraryRoute::class,
    ),
    PREMIUM(
        unselectedIcon = SpotlightIcons.Premium,
        selectedIcon = SpotlightIcons.Premium,
        title = R.string.premium,
        route = PremiumRoute::class,
    ),
}