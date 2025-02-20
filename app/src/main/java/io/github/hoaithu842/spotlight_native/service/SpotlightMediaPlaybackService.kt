package io.github.hoaithu842.spotlight_native.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaStyleNotificationHelper
import dagger.hilt.android.AndroidEntryPoint
import io.github.hoaithu842.spotlight_native.R
import io.github.hoaithu842.spotlight_native.manager.MediaPlayerController
import javax.inject.Inject

@AndroidEntryPoint
class SpotlightMediaPlaybackService : Service() {
    @Inject
    lateinit var mediaPlayerController: MediaPlayerController

    private val binder = MediaPlaybackBinder()

    inner class MediaPlaybackBinder : Binder() {
        fun getService(): SpotlightMediaPlaybackService = this@SpotlightMediaPlaybackService
    }

    override fun onBind(intent: Intent?): IBinder {
        sendNotification("")
        return binder
    }

    @OptIn(UnstableApi::class)
    private fun sendNotification(track: String) {
        val notification = NotificationCompat.Builder(this, "channel_id")
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .addAction(R.drawable.play_previous, "Previous", createPrevPendingIntent())
            .addAction(R.drawable.play, "Pause", createPlayPausePendingIntent())
            .addAction(R.drawable.play_next, "Next", createNextPendingIntent())
            .setStyle(
                MediaStyleNotificationHelper.MediaStyle(mediaPlayerController.mediaSession)
                    .setShowActionsInCompactView(1)
            )
            .setContentTitle("Wonderful Music")
            .setContentText("My Awesome Band")
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_launcher_background
                )
            )
            .setOngoing(true) // Prevent swipe when playing
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
        startForeground(1, notification)
    }

    private fun createPrevPendingIntent(): PendingIntent {
        val intent = Intent(this, SpotlightMediaPlaybackService::class.java).apply {
            action = "prev"
        }
        return PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createPlayPausePendingIntent(): PendingIntent {
        val intent = Intent(this, SpotlightMediaPlaybackService::class.java).apply {
            action = "play_pause"
        }
        return PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNextPendingIntent(): PendingIntent {
        val intent = Intent(this, SpotlightMediaPlaybackService::class.java).apply {
            action = "next"
        }
        return PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}