package io.github.hoaithu842.spotlight_native

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightNavigationBar
import io.github.hoaithu842.spotlight_native.ui.designsystem.SpotlightNavigationBarItem
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme

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

            SpotlightTheme {
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
                        SpotlightNavHost(navHostController = navController)
                    }
                }
            }
        }
    }
}