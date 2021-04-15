package com.mahmoudbashir.azan_app.pojo

data class location(
    val latitude: Double,
    val longitude: Double,
    val elevation: Int,
    val city: String,
    val country: String,
    val country_code: String,
    val timezone: String,
    val local_offset: Int
)
