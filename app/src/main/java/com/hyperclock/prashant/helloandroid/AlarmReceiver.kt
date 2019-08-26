package com.hyperclock.prashant.helloandroid

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val service = Intent(context, AlarmSchedulingService::class.java)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a")
        service.putExtra("RepeatingAlarm","Time:"+dateFormat.format(Date()))
        AlarmSchedulingService().enqWork(context!!,service)
        Log.d("AlarmReceiver","entered onReceive")
    }

    fun setRepeatingAlarmReminder(context:Context,time:Long) {
        //Below code is to set alarm in android programmatically with repeating interval of 1 minute
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,(1000 * 60).toLong(), pendIntent)
    }

    fun setExactAlarmReminder(context:Context,time:Long) {
        //Below code is to set alarm in android programmatically with repeating interval of 1 minute
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time,pendIntent)
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, AlarmReceiver::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(sender)
    }
}


