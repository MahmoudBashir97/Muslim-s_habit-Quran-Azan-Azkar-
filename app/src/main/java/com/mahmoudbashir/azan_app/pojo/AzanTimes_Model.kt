package com.mahmoudbashir.azan_app.pojo

import com.google.gson.annotations.SerializedName

data class AzanTimes_Model(
    @SerializedName("code")
    val code : Int,
    @SerializedName("status")
    val status:String,
    @SerializedName("results")
    val results:results,
)
