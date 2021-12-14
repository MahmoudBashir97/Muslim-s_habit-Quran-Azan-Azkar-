package com.mahmoudbashir.azan_app.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.azan.Azan
import com.azan.Method
import com.azan.astrologicalCalc.Location
import com.azan.astrologicalCalc.SimpleDate
import com.mahmoudbashir.azan_app.AzanApplication
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.di.components.DaggerTimesComponent
import com.mahmoudbashir.azan_app.di.components.TimesComponent
import com.mahmoudbashir.azan_app.di.module.AzanModule
import com.mahmoudbashir.azan_app.repository.AzanRepository
import com.mahmoudbashir.azan_app.viewmodel.AzanViewModel
import com.mahmoudbashir.azan_app.viewmodel.ViewModelProviderFactory
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: AzanViewModel
    private var currentPray: MutableLiveData<String> = MutableLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.WHITE
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        injectDagger()
        setUpViewModel()


        val today = SimpleDate(GregorianCalendar())
        val location = Location(30.045411, 31.236735, 2.0, 0)
        val azan = Azan(location, Method.EGYPT_SURVEY)
        val prayerTimes = azan.getPrayerTimes(today)
        val imsaak = azan.getImsaak(today)
        println("----------------results------------------------")
        Log.d("ELTaherTime : ","date ---> " + today.day + " / " + today.month + " / " + today.year +
                " imsaak ---> $imsaak"
        +" Fajr ---> " + prayerTimes.fajr()
        +" sunrise --->" + prayerTimes.shuruq()
        +" Zuhr --->" + prayerTimes.thuhr()
        +" Asr --->" + prayerTimes.assr()
        +" Maghrib --->" + prayerTimes.maghrib()
        +" ISHA  --->" + prayerTimes.ishaa())
        println("----------------------------------------")


    }


    private fun setUpViewModel() {
        val repo = AzanRepository(AzanModule(this.application).providesRoomDatabase())
        val viewModelProviderFactory = ViewModelProviderFactory(application, repo)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(AzanViewModel::class.java)
    }





    private fun injectDagger() {
        AzanApplication.instance.timeComp.inject(this)

        val comp: TimesComponent = DaggerTimesComponent.create()
        comp.inject(this)
    }
}