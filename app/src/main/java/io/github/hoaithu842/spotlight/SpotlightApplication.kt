package io.github.hoaithu842.spotlight

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.content.getSystemService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SpotlightApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val channel =
            NotificationChannel(
                "channel_id",
                "channel_name",
                NotificationManager.IMPORTANCE_DEFAULT,
            )

        val notificationManager = getSystemService<NotificationManager>()!!
        notificationManager.createNotificationChannel(channel)
    }
}
