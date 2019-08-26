package com.hyperclock.prashant.helloandroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var videoView: VideoView

    companion object {
        val APP_TAG = this.javaClass.name

        val DATE_STRING = "2019-08-26 15:08:52"
    }

    override fun onStart() {
        videoView.start()
        super.onStart()
    }

    override fun onStop() {
        videoView.stopPlayback()
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //if time picker is used within the application to set the time
        /*val timePicker :TimePicker = findViewById(R.id.timePicker1)
        val min = timePicker.currentMinute
        val hour = timePicker.currentHour
        val dateString = "2019-08-24 $hour:$min:00"*/

        val loadingText: TextView = findViewById(R.id.loading_text)
        val timeText: TextView = findViewById(R.id.time_view)
        val descText: TextView = findViewById(R.id.desct_text)
        val natyaText: TextView = findViewById(R.id.wish_text_heading)

        //dealing with the video
        videoView = findViewById(R.id.video_view)
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video))
        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        videoView.requestFocus()

        // Register the channel with the system
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //creating notification channel
        Notification.createNotificationChannel(getString(R.string.channel_name),getString(R.string.channel_description),notificationManager)


        val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val currentTimeinMillis = System.currentTimeMillis()

        try {
            val date: Date = dateFormat.parse(DATE_STRING)
            val scheduledTimeInMillis = date.time
            val alarmReceiver = AlarmReceiver()

            if (currentTimeinMillis < 100 + scheduledTimeInMillis) {
                //this fires in the past
                videoView.visibility = View.GONE
                alarmReceiver.setExactAlarmReminder(this, scheduledTimeInMillis)
            } else {
                //this fires at the time on or after the scheduled time
                loadingText.visibility = View.GONE
                alarmReceiver.cancelAlarm(this)

                val newtimer = object : CountDownTimer(40000, 1000) {
                    var count = 0

                    override fun onTick(millisUntilFinished: Long) {
                        count++
                        val calender = Calendar.getInstance()
                        timeText.text = (calender.get(Calendar.HOUR).toString()+":" +calender.get(Calendar.MINUTE) +":" + calender.get(Calendar.SECOND))

                        when (count) {
                            20 -> {
                                val snackbar = Snackbar.make(videoView, "Natya it is..", Snackbar.LENGTH_SHORT)
                                snackbar.show()
                            }
                            30 -> {
                                val snackbar = Snackbar.make(videoView, "Have a nice day", Snackbar.LENGTH_SHORT)
                                snackbar.show()
                            }
                            8 -> natyaText.visibility = View.VISIBLE
                            12 -> {
                                descText.visibility = View.VISIBLE
                                AnimationUtils.loadAnimation(application, R.anim.animation_reverse).also { anim ->
                                    findViewById<TextView>(R.id.desct_text).startAnimation(anim)
                                }
                            }
                        }

                    }

                    override fun onFinish() {
                        //nothing goes here
                        finish()
                    }
                }
                newtimer.start()
            }

        } catch (exception: ParseException) {
            exception.printStackTrace()
        }

    }
}
