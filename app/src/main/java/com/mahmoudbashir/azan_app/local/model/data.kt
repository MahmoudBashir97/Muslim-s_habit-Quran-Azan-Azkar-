package com.mahmoudbashir.azan_app.local.model

import com.google.gson.annotations.SerializedName

data class data(
    @SerializedName("category")
    val category: String,
    @SerializedName("count")
    val count: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("reference")
    val reference: String,
    @SerializedName("zekr")
    val zekr: String
)
