package com.mahmoudbashir.azan_app.retrofit

import androidx.lifecycle.LiveData
import com.mahmoudbashir.azan_app.pojo.AzanTimes_Model
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServiceInterface {

    @Headers("Accept: application/json")
    @GET("v2/times/this_month.json")
    suspend fun getAzanOfMonthTimes(
        @Query("city")
        city : String,
        @Query("timeformat")
        timeformat:Int
    ):Response<AzanTimes_Model>
}