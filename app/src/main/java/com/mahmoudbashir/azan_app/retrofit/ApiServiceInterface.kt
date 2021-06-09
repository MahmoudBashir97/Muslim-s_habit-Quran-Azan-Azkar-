package com.mahmoudbashir.azan_app.retrofit

import androidx.lifecycle.LiveData
import com.mahmoudbashir.azan_app.pojo.AzanTimes_Model
import com.mahmoudbashir.azan_app.pojo.quran.Quran_Model
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
        timeformat:Int,
        @Query("school")
        school:Int
    ):Response<AzanTimes_Model>

    @Headers("Accept: application/json")
    @GET("v2/times/this_month.json")
    suspend fun getAzanOfMonthTimesByLocation(
        @Query("longitude")
        longitude : String,
        @Query("latitude")
        latitude:String,
        @Query("elevation")
        elevation:Int,
        @Query("timeformat")
        timeformat:Int,
        @Query("school")
        school:Int
    ):Response<AzanTimes_Model>


    @Headers("Accept: application/json")
    @GET("v1/quran/quran-uthmani")
    suspend fun getQuranData(
    ):Response<Quran_Model>
}