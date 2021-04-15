package com.mahmoudbashir.azan_app.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoudbashir.azan_app.pojo.date
import com.mahmoudbashir.azan_app.pojo.datetime
import com.mahmoudbashir.azan_app.pojo.location
import com.mahmoudbashir.azan_app.pojo.settings

class Converters {

    @TypeConverter
    public fun fromDateTimeToString(datetime: List<datetime>): String? {
        return Gson().toJson(datetime)
    }

    @TypeConverter
    public fun fromStringToDateTime(stdatetime: String): List<datetime> {
        val listType = object :TypeToken<List<datetime>>() {}.type
        return Gson().fromJson(stdatetime,listType)
    }

    @TypeConverter
    public fun fromdateToString(d:date):String{
        return Gson().toJson(d)
    }

    @TypeConverter
    public fun fromStringTodate(std: String):date{
        return Gson().fromJson(std,date::class.java)
    }

    @TypeConverter
    public fun fromlocationToString(loc:location):String{
        return Gson().toJson(loc)
    }

    @TypeConverter
    public fun fromStringTolocation(stLoca: String):location{
        return Gson().fromJson(stLoca,location::class.java)
    }


    @TypeConverter
    public fun fromsettingsToString(sett:settings):String{
        return Gson().toJson(sett)
    }

    @TypeConverter
    public fun fromStringTosettings(stsettings: String):settings{
        return Gson().fromJson(stsettings,settings::class.java)
    }

}