package com.mahmoudbashir.azan_app.local.model.residents

import com.google.gson.annotations.SerializedName

data class data(
    @SerializedName("country")
    val country:String,
    @SerializedName("cities")
    val cities: List<String>
)
