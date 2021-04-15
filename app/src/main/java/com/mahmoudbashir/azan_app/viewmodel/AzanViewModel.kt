package com.mahmoudbashir.azan_app.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.mahmoudbashir.azan_app.AzanApplication
import com.mahmoudbashir.azan_app.pojo.AzanTimes_Model
import com.mahmoudbashir.azan_app.pojo.results
import com.mahmoudbashir.azan_app.repository.AzanRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AzanViewModel(app:Application,private val repo:AzanRepository):AndroidViewModel(app) {

    val getAllData:LiveData<results> = repo.getAllDataTimes
    suspend fun getAzanTimes(city:String,timeformat:Int):Response<AzanTimes_Model>{
        return repo.getAzanTimes(city,timeformat)
    }

    fun insert(result: results)=viewModelScope.launch {
        repo.insert(result)
    }
    public fun hasInternetConnection():Boolean{
        val connectivityManager = getApplication<AzanApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}