package com.mahmoudbashir.azan_app.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import com.mahmoudbashir.azan_app.R
import java.text.SimpleDateFormat
import java.util.*

class IntentServ : IntentService("MyIntentService") {
    init {
        instance = this
    }
    companion object{
        private lateinit var instance:IntentServ
        var isRunning = false

        fun stopService(){
            Log.d("MyIntentService","Service is Stopping...")
            isRunning = false
            instance.stopSelf()
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        try {
            isRunning = true
            while (isRunning){

               val st_fajr= intent?.getStringExtra("fajr_time")
                val st_zohr= intent?.getStringExtra("zohr_time")
                val st_asr= intent?.getStringExtra("asr_time")
                val st_maghrib= intent?.getStringExtra("maghrib_time")
                val st_eshaa= intent?.getStringExtra("eshaa_time")
                updateTimeOnEachSecond(this,st_fajr,st_zohr,st_asr,st_maghrib,st_eshaa)

                Log.d("MyIntentService","Service is Running...")
               Thread.sleep(60000)
            }
        }catch (e:InterruptedException){
            Thread.currentThread().interrupt()
        }
    }

    private fun updateTimeOnEachSecond(context: Context,st_fajr: String?,
                                       st_zohr: String?,
                                       st_asr: String?,
                                       st_maghrib: String?,
                                       st_eshaa: String?) {
        val sdf = SimpleDateFormat("hh:mm aa")
        val currenttime = sdf.format(Date())
        Log.d("currentTime", "current Time : $currenttime")
        when (currenttime) {
             st_fajr -> {
                Log.d("praytime", "fajr pray now")
                val mediaplayer = MediaPlayer.create(context,R.raw.azan_abdelbaset)
                mediaplayer.start()
                return
            }
            st_zohr -> {
                Log.d("praytime", "zohr pray now")
                val mediaplayer = MediaPlayer.create(context,R.raw.azan_abdelbaset)
                mediaplayer.start()
                return
            }
            st_asr -> {
                Log.d("praytime", "asr pray now")
                val mediaplayer = MediaPlayer.create(context,R.raw.azan_abdelbaset)
                mediaplayer.start()
                return
            }
            st_maghrib -> {
                Log.d("praytime", "maghrib pray now")
                val mediaplayer = MediaPlayer.create(context,R.raw.azan_abdelbaset)
                mediaplayer.start()
                return
            }
            st_eshaa -> {
                Log.d("praytime", "eshaa pray now")
                val mediaplayer = MediaPlayer.create(context,R.raw.azan_abdelbaset)
                mediaplayer.start()
                return
            }
        }

        /*val timer = Timer()
        timer.schedule(
            object : TimerTask() {

                override fun run() {

                }
            },
            0, 1000,
        )*/
    }
}