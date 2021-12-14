package com.mahmoudbashir.azan_app.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.mahmoudbashir.azan_app.R

class PlayAndStopAzanMedia : BroadcastReceiver() {
    lateinit var mediaplayer: MediaPlayer
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("recievedCorr : ","mmmm")
        val status = intent?.getStringExtra("status")
        mediaplayer = MediaPlayer.create(context, R.raw.azan_abdelbaset)
        mediaplayer.start()
        if (status == "play"){
               mediaplayer.start()
        }else{
            mediaplayer.stop()
        }
    }
}