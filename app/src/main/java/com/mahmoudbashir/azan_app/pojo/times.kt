package com.mahmoudbashir.azan_app.pojo

import com.google.gson.annotations.SerializedName


data class times(
    @SerializedName("Imsak")
    val Imsak: String,
    @SerializedName("Sunrise")
    val Sunrise: String,
    @SerializedName("Fajr")
    val Fajr: String,
    @SerializedName("Dhuhr")
    val Dhuhr: String,
    @SerializedName("Asr")
    val Asr: String,
    @SerializedName("Sunset")
    val Sunset: String,
    @SerializedName("Maghrib")
    val Maghrib: String,
    @SerializedName("Isha")
    val Isha: String,
    @SerializedName("Midnight")
    val Midnight: String,
)
