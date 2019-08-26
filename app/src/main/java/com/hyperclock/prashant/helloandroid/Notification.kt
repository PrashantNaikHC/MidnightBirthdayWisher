package com.hyperclock.prashant.helloandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat

class Notification {

    companion object{
        val CHANNEL_ID = "TEMP_CHANNEL_ID"

        fun createNotification(notificationManager : NotificationManager, pendingIntent : PendingIntent, context:Context, title:String, content:String){

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setTicker("Notification Listener Service")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            notificationManager.notify(System.currentTimeMillis().toInt(), notification.build())
        }

        fun createNotificationChannel(name:String, descriptionText:String, notificationManager: NotificationManager) {

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}