package com.mahmoudbashir.azan_app.pojo

import com.google.gson.annotations.SerializedName


data class date(
    @SerializedName("timestamp")
    val timestamp: Double,
    @SerializedName("gregorian")
    val gregorian: String,
    @SerializedName("hijri")
    val hijri: String,
)
