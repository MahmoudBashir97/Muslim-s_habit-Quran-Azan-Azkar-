package com.mahmoudbashir.prefs

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs (context: Context){
    private val preferences: SharedPreferences = context.getSharedPreferences("Azan_app",Context.MODE_PRIVATE)
    private val  prayName = "null"

    var prayNamePref: String?
        get() = preferences.getString(prayName, "")
        set(value) = preferences.edit().putString(prayName, value).apply()
}