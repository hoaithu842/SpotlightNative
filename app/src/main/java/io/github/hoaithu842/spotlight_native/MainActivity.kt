package io.github.hoaithu842.spotlight_native

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint
import io.github.hoaithu842.spotlight_native.manager.NetworkMonitor
import io.github.hoaithu842.spotlight_native.presentation.viewmodel.PlayerViewModel
import io.github.hoaithu842.spotlight_native.service.SpotlightMediaPlaybackService
import io.github.hoaithu842.spotlight_native.ui.theme.SpotlightTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var networkMonitor: NetworkMonitor
    private val playerViewModel: PlayerViewModel by viewModels()
    private val mainViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.setContext(this)
        enableEdgeToEdge()
        setContent {
            val isOffline by networkMonitor.isOnline.collectAsState(initial = true)
            val profile by mainViewModel.profile.collectAsState()
            SpotlightTheme {
                SpotlightContent(
                    userProfile = profile,
                    isOffline = isOffline,
                    onAvatarClick = {
                    },
                    onLoginClick = {
                        mainViewModel.login()
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sessionToken =
            SessionToken(this, ComponentName(this, SpotlightMediaPlaybackService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                playerViewModel.setController(controllerFuture.get())
            },
            MoreExecutors.directExecutor()
        )
    }

    override fun onStop() {
        super.onStop()
        val sessionToken =
            SessionToken(this, ComponentName(this, SpotlightMediaPlaybackService::class.java))
        val controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        MediaController.releaseFuture(controllerFuture)
    }
}