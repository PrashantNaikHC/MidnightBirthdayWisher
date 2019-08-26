package com.hyperclock.prashant.helloandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BootBroadcastReceiver","entered onReceive")
        val alarmReceiver = AlarmReceiver()
        if(intent?.action.equals(Intent.ACTION_BOOT_COMPLETED)){
            alarmReceiver.setRepeatingAlarmReminder(context!!,System.currentTimeMillis())
        }
    }
}