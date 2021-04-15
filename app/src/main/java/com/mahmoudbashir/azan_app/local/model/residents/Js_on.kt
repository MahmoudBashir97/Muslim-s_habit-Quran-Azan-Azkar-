package com.mahmoudbashir.azan_app.local.model.residents

import com.google.gson.annotations.SerializedName

data class Js_on(
    @SerializedName("error")
    val error: String,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("data")
    val data: List<data>
)
