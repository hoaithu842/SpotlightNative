package io.github.hoaithu842.spotlight_native.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SpotlightNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = HomeRoute,
    ) {
        homeScreen()
        searchScreen()
        libraryScreen()
        premiumScreen()
    }
}