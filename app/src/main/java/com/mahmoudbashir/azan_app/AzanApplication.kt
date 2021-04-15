package com.mahmoudbashir.azan_app

import android.app.Application
import com.mahmoudbashir.azan_app.di.components.DaggerTimesComponent
import com.mahmoudbashir.azan_app.di.components.TimesComponent
import com.mahmoudbashir.prefs.SharedPrefs

class AzanApplication : Application(){

    companion object{
        var prefs:SharedPrefs?=null
        public lateinit var instance:AzanApplication
    }

    lateinit var timeComp:TimesComponent
    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = SharedPrefs(applicationContext)
        timeComp = DaggerTimesComponent
            .builder()
            .build()
    }
}