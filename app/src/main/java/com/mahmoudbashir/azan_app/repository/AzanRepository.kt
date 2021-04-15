package com.mahmoudbashir.azan_app.repository

import androidx.lifecycle.LiveData
import com.mahmoudbashir.azan_app.pojo.AzanTimes_Model
import com.mahmoudbashir.azan_app.pojo.results
import com.mahmoudbashir.azan_app.retrofit.RetrofitInstance
import com.mahmoudbashir.azan_app.room.AzanTimesDatabase
import retrofit2.Response

class AzanRepository(
    private val db:AzanTimesDatabase
) {
    suspend fun getAzanTimes(city : String,timeformat:Int):Response<AzanTimes_Model> = RetrofitInstance.api.getAzanOfMonthTimes(city,timeformat)

    suspend fun insert(results: results)=db.dao().insert(results)

    val getAllDataTimes:LiveData<results> = db.dao().getAllDataTimesStored()

    suspend fun getAllDataInsidebroadcast():results = db.dao().getAllDataForbroadCast()
}