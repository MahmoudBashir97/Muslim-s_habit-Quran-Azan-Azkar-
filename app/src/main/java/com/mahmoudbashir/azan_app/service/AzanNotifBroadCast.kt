package com.mahmoudbashir.azan_app.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.AlarmClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.prefs.SharedPreference
import java.text.SimpleDateFormat
import java.util.*

class AzanNotifBroadCast : BroadcastReceiver() {
    lateinit var mediaplayer:MediaPlayer
    lateinit var notificationManagerCompat : NotificationManagerCompat

    override fun onReceive(context: Context?, intent: Intent?) {
        val st_fajr= intent?.getStringExtra("fajr_time")
        val st_zohr= intent?.getStringExtra("zohr_time")
        val st_asr= intent?.getStringExtra("asr_time")
        val st_maghrib= intent?.getStringExtra("maghrib_time")
        val st_eshaa= intent?.getStringExtra("eshaa_time")

        notificationManagerCompat = NotificationManagerCompat.from(context!!)
        mediaplayer = MediaPlayer.create(context, R.raw.azan_abdelbaset)
        val alarm_status =  SharedPreference.getInastance(context).alarmStatus


        val stopAction = intent?.getStringExtra("stopAction")
        if (stopAction == "stop"){
            mediaplayer.stop()
            /*val i = Intent(context,MediaPlayerService::class.java)
                        context.stopService(i)*/
            notificationManagerCompat.cancel(200)
            android.os.Process.killProcess(android.os.Process.myPid())

        }else{
            if (alarm_status){
                Log.d("repeating:", "repeating every one minute ")
                updateTimeOnEachSecond(context!!, st_fajr, st_zohr, st_asr, st_maghrib, st_eshaa)
            }
        }

    }



    private fun updateTimeOnEachSecond(
        context: Context,
        st_fajr: String?,
        st_zohr: String?,
        st_asr: String?,
        st_maghrib: String?,
        st_eshaa: String?
    ) {

        val sdf = SimpleDateFormat("hh:mm aa")
        val currenttime = sdf.format(Date())
        Log.d("currentTime", "current Time : $currenttime , $st_eshaa")

        when (currenttime) {
            st_fajr -> {
                notificationSetting(context,"fajr")
                return
            }
            st_zohr -> {
                notificationSetting(context,"zohr")
                return
            }
            st_asr -> {
                notificationSetting(context,"asr")
                return
            }
            st_maghrib -> {
                notificationSetting(context,"maghrib")
                return
            }
            st_eshaa -> {
                notificationSetting(context,"eshaa")
                return
            }
        }
    }

    private fun notificationSetting(context: Context,pray_name:String){
        var title  = "موعد الآذان الآن"
        var subTitle= ""
        when(pray_name){
            "fajr"->{

                subTitle = "حان الآن موعد آذان الفجر"
            }
            "zohr"->{
                subTitle = "حان الآن موعد آذان الظهر"
            }
            "asr"->{
                subTitle = "حان الآن موعد آذان العصر"
            }
            "maghrib"->{
                subTitle = "حان الآن موعد آذان المغرب"
            }
            "eshaa"->{
                subTitle = "حان الآن موعد آذان العشاء"
            }
        }

        Log.d("praytime", "pray now $pray_name")

        //context.startService(Intent(context,MediaPlayerService::class.java))
        mediaplayer.start()

        val action = Intent(context,AzanNotifBroadCast::class.java).apply {
            action = AlarmClock.ACTION_DISMISS_ALARM
            putExtra("stopAction","stop")
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, action, 0)

        val builder = NotificationCompat.Builder(context, "notifyme")
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(subTitle)
            .setSmallIcon(R.drawable.logo)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(1,"Stop",pendingIntent)


        notificationManagerCompat.notify(200, builder.build())

    }

}