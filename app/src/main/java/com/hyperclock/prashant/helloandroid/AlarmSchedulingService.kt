package com.hyperclock.prashant.helloandroid

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.JobIntentService
import android.support.v4.app.NotificationCompat
import android.util.Log


class AlarmSchedulingService : JobIntentService() {

    //constant ID to recognize the job
    val JOB_ID = 1000
    fun enqWork(context: Context , work:Intent) {
        WakeLocker.acquire(context)
        enqueueWork(context, AlarmSchedulingService::class.java, JOB_ID, work)
        Log.d("AlarmSchedulingService","entered enqWork")
    }

    override fun onHandleWork(intent: Intent) {
        Log.d("AlarmSchedulingService","entered onHandleWork"+intent.extras.getString("RepeatingAlarm"))

        // Create an explicit intent for an Activity in your app
        val pendIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        //creating pending intent
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, pendIntent, 0)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //creating notification
        Notification.createNotification(notificationManager, pendingIntent, this, resources.getString(R.string.wish_text),resources.getString(R.string.description))

        //intent to start app
        val intent = packageManager.getLaunchIntentForPackage("com.hyperclock.prashant.helloandroid")
        startActivity(intent)
    }
}