package io.github.hoaithu842.spotlight_native

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import io.github.hoaithu842.spotlight_native.manager.NetworkMonitor
import io.github.hoaithu842.spotlight_native.navigation.SpotlightNavHost
import io.github.hoaithu842.spotlight_native.navigation.TopLevelDestination
import io.github.hoaithu842.spotlight_native.navigation.navigateToHomeScreen
import io.github.hoaithu842.spotlight_native.navigation.navigateToLibraryScreen
import io.github.hoaithu842.spotlight_native.navigation.navigateToPremiumScreen
import io.github.hoaithu842.spotlight_native.navigation.navigateToSearchScreen
import io.github.hoaithu842.spotlight_native.presentation.component.FullsizePlayer
import io.github.hoaithu842.spotlight_native.presentation.component.MinimizedPlayer
import io.github.hoaithu842.spotlight_native.presentation.designsystem.CustomDrawerState
import io.github.hoaithu842.spotlight_native.presentation.designsystem.HomeScreenDrawer
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightDimens
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightNavigationBar
import io.github.hoaithu842.spotlight_native.presentation.designsystem.SpotlightNavigationBarItem
import io.github.hoaithu842.spotlight_native.presentation.designsystem.isOpened
import io.github.hoaithu842.spotlight_native.presentation.designsystem.opposite
import io.github.hoaithu842.spotlight_native.presentation.theme.SpotlightTheme
import io.github.hoaithu842.spotlight_native.service.SpotlightMediaPlaybackService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Service
    private var mediaPlaybackService: SpotlightMediaPlaybackService? = null
    private var isBound = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mediaPlaybackService =
                (service as SpotlightMediaPlaybackService.MediaPlaybackBinder).getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private val viewModel: MainActivityViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle(initialValue = false)
            val currentPosition by viewModel.currentPosition.collectAsStateWithLifecycle(
                initialValue = 0
            )
            val currentSong by viewModel.currentSong.collectAsStateWithLifecycle()
            val isOffline by networkMonitor.isOnline.collectAsState(initial = true)
            var isNavBarDisplaying by rememberSaveable { mutableStateOf(true) }
            val density = LocalDensity.current
            val scaffoldState = rememberBottomSheetScaffoldState()
            val navController = rememberNavController()
            var currentDestination by rememberSaveable { mutableStateOf(TopLevelDestination.HOME) }
            val coroutineScope = rememberCoroutineScope()
            var drawerState by remember { mutableStateOf(CustomDrawerState.Closed) }
            val configuration = LocalConfiguration.current
            val newDensity = LocalDensity.current.density
            val screenWidth = remember {
                derivedStateOf { (configuration.screenWidthDp * newDensity).roundToInt() }
            }
            val offsetValue by remember { derivedStateOf { (screenWidth.value / 4.5).dp } }
            val animatedOffset by animateDpAsState(
                targetValue = if (drawerState.isOpened()) offsetValue else 0.dp,
                label = "",
            )
            val snackbarHostState = remember { SnackbarHostState() }
            LaunchedEffect(isOffline) {
                snackbarHostState.showSnackbar(isOffline.toString())
            }

            LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
                if (scaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
                    isNavBarDisplaying = true
                }
            }

            LaunchedEffect(Unit) {
                val intent = Intent(this@MainActivity, SpotlightMediaPlaybackService::class.java)
                startService(intent)
                bindService(intent, connection, BIND_AUTO_CREATE)
            }

            SpotlightTheme {
                BackHandler(enabled = drawerState.isOpened()) {
                    drawerState = CustomDrawerState.Closed
                }
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                        .fillMaxSize()
                ) {
                    HomeScreenDrawer()
                    Scaffold(
                        modifier = Modifier
                            .offset(x = animatedOffset)
                            .clickable(enabled = drawerState == CustomDrawerState.Opened) {
                                drawerState = CustomDrawerState.Closed
                            },
                        bottomBar = {
                            AnimatedVisibility(
                                visible = isNavBarDisplaying,
                                enter = slideInVertically {
                                    with(density) { -40.dp.roundToPx() }
                                } + expandVertically(
                                    expandFrom = Alignment.Top
                                ) + fadeIn(
                                    initialAlpha = 0.3f
                                ),
                                exit = slideOutVertically() + shrinkVertically() + fadeOut()
                            ) {
                                SpotlightNavigationBar {
                                    TopLevelDestination.entries.forEach { destination ->
                                        SpotlightNavigationBarItem(
                                            title = stringResource(destination.title),
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
                            }
                        },
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .padding(bottom = innerPadding.calculateBottomPadding())
                                .fillMaxSize()
                        ) {
                            BottomSheetScaffold(
                                scaffoldState = scaffoldState,
                                sheetPeekHeight = SpotlightDimens.MinimizedPlayerHeight,
                                sheetShape = RoundedCornerShape(0.dp),
                                sheetDragHandle = {},
                                sheetShadowElevation = 0.dp,
                                sheetContent = {
                                    AnimatedContent(
                                        targetState = scaffoldState.bottomSheetState.currentValue,
                                        label = "",
                                    ) {
                                        when (it) {
                                            SheetValue.Hidden -> {}
                                            SheetValue.Expanded -> {
                                                FullsizePlayer(
                                                    isPlaying = isPlaying,
                                                    song = currentSong,
                                                    currentPosition = currentPosition,
                                                    duration = viewModel.getDuration(),
                                                    onMinimizeClick = {
                                                        coroutineScope.launch {
                                                            isNavBarDisplaying = true
                                                            delay(100)
                                                            scaffoldState.bottomSheetState.partialExpand()
                                                        }
                                                    },
                                                    onPrevClick = viewModel::prev,
                                                    onMainFunctionClick = viewModel::process,
                                                    onNextClick = viewModel::next,
                                                )
                                            }

                                            SheetValue.PartiallyExpanded -> {
                                                MinimizedPlayer(
                                                    isPlaying = isPlaying,
                                                    song = currentSong,
                                                    currentPosition = currentPosition,
                                                    duration = viewModel.getDuration(),
                                                    onPlayerClick = {
                                                        coroutineScope.launch {
                                                            isNavBarDisplaying = false
                                                            scaffoldState.bottomSheetState.expand()
                                                        }
                                                    },
                                                    onMainFunctionClick = viewModel::process,
                                                )
                                            }
                                        }
                                    }
                                },
                                snackbarHost = { SnackbarHost(snackbarHostState) },
                                sheetContainerColor = Color.Transparent,
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize(),
                            ) {
                                SpotlightNavHost(
                                    navHostController = navController,
                                    onAvatarClick = { drawerState = drawerState.opposite() },
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(this@MainActivity, SpotlightMediaPlaybackService::class.java)
        stopService(intent)
        unbindService(connection)
    }
}