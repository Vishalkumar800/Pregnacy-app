package com.rach.trackmypregapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.rach.trackmypregapp.R
import javax.inject.Inject

class MyNotification @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager
) {

    private val channelId = "FCM100"
    private val channelName = "FCMMessage"

//    private val notificationManager =
//        context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: NotificationCompat.Builder


    fun fireNotification(
        title: String,
        message: String
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            notificationChannel.enableVibration(true)
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 300, 200, 1000)

        }

        notificationBuilder = NotificationCompat.Builder(context, channelId)
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(message)
        notificationBuilder.setAutoCancel(true)

        notificationManager.notify(100, notificationBuilder.build())
    }
}