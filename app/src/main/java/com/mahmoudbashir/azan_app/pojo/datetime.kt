package com.mahmoudbashir.azan_app.pojo

import com.google.gson.annotations.SerializedName

data class datetime(
    @SerializedName("times")
    val times:times,
    @SerializedName("date")
    val date:date
)

