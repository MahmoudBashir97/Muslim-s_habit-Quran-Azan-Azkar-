package com.mahmoudbashir.azan_app.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mahmoudbashir.azan_app.ui.MainActivity
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.databinding.FragmentAzanBinding
import com.mahmoudbashir.azan_app.extentions.NetworkAbility
import com.mahmoudbashir.azan_app.pojo.results
import com.mahmoudbashir.azan_app.service.AzanNotifBroadCast
import com.mahmoudbashir.azan_app.viewmodel.AzanViewModel
import com.mahmoudbashir.prefs.SharedPreference
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*


class Azan_Fragment : Fragment() {

    lateinit var azanBinding: FragmentAzanBinding
    private lateinit var viewModel: AzanViewModel
    private var currentPray: MutableLiveData<String> = MutableLiveData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        azanBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_azan_, container, false)
        viewModel = (activity as MainActivity).viewModel

        azanBinding.isConnected = true
        //handleNetworkChanges()
        getStoredDataToView()
        handleNetworkChanges()
        handleSwitchAlarmStatus()

        azanBinding.settingsBtn.setOnClickListener{
            SharedPreference.getInastance(context).savePath("azan")
            findNavController().navigate(Azan_FragmentDirections.actionAzanFragmentToAddInfoFragment())
        }

        return azanBinding.root
    }

    private fun handleSwitchAlarmStatus(){
        val status_alarm = SharedPreference.getInastance(this.requireContext()).alarmStatus
        azanBinding.btnSwitchNotify.isChecked = status_alarm
        val status = if (status_alarm) "(يعمل)" else "(متوقف)"
        azanBinding.txtAlarmStatus.text = status
        azanBinding.btnSwitchNotify.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "(يعمل)" else "(متوقف)"
            azanBinding.txtAlarmStatus.text = status
            SharedPreference.getInastance(this.requireContext()).save_alarm_status(isChecked)
        }
    }

    private fun getStoredDataToView() {

        viewModel.getAllData.observe(viewLifecycleOwner, Observer {
            val sd = SimpleDateFormat("yyyy-MM-dd")
            val d = sd.format(Date())
            if (it != null) {
                for (element in it.datetime) {
                    if (d.equals(element.date.gregorian)) {
                        val fajr = element.times.Fajr
                        val zohr = element.times.Dhuhr
                        val asr = element.times.Asr
                        val maghrib = element.times.Maghrib
                        val eshaa = element.times.Isha
                        setUpViews(fajr, zohr, asr, maghrib, eshaa)
                        Log.d("today_date : ", "date :${element.date.gregorian}")

                        // start alarm service for alarming user with pray realtime
                        //startService(fajr, zohr, asr, maghrib, eshaa)
                    }
                }
            }
        })
    }
    private suspend fun getAndStore(city: String) {
        val schoolId = SharedPreference.getInastance(context).timesSchoolId
        val lat = SharedPreference.getInastance(context).lat
        val lng = SharedPreference.getInastance(context).lng
        GlobalScope.launch(Dispatchers.IO) {
            if (viewModel.getAzanTimes(lat,lng,333, 1,schoolId).isSuccessful) {
                viewModel.getAzanTimes(lat,lng,333, 1,schoolId).body()?.let {
                    Log.d("timesAzan: ", "result: ${it.results.location.city}")
                    val result: results = it.results
                    viewModel.insert(result)
                }
                Log.d("timesAzan: ", "Worked success")
            }
        }
    }

    private fun setUpViews(
        st_fajr: String,
        st_zohr: String,
        st_asr: String,
        st_maghrib: String,
        st_eshaa: String
    ) {
        azanBinding.txtFajrTime.text = st_fajr
        azanBinding.txtZohrTime.text = st_zohr
        azanBinding.txtAsrTime.text = st_asr
        azanBinding.txtMaghribTime.text = st_maghrib
        azanBinding.txtEshaaTime.text = st_eshaa

        val formatter = SimpleDateFormat("hh:mm aa");
        val cTime = formatter.format(Date())
        val ft = formatter.parse(cTime) as Date
        val fajrdate = formatter.parse(st_fajr) as Date
        val zohrdate = formatter.parse(st_zohr) as Date
        val asrsdate = formatter.parse(st_asr) as Date
        val maghribdate = formatter.parse(st_maghrib) as Date
        val eshaadate = formatter.parse(st_eshaa) as Date

        when {
            fajrdate.after(ft) -> {
                azanBinding.txtNextPrayTime.text = st_fajr
                azanBinding.txtNextPrayName.text = "الفجر"
                Log.d("comparing", "fajr time")
            }
            zohrdate.after(ft) -> {
                azanBinding.txtNextPrayTime.text = st_zohr
                azanBinding.txtNextPrayName.text = "الظهر"
                Log.d("comparing", "zohr time")
            }
            asrsdate.after(ft) -> {
                azanBinding.txtNextPrayTime.text = st_asr
                azanBinding.txtNextPrayName.text = "العصر"
                Log.d("comparing", "asr time")
            }
            maghribdate.after(ft) -> {
                azanBinding.txtNextPrayTime.text = st_maghrib
                azanBinding.txtNextPrayName.text = "المغرب"
                Log.d("comparing", "maghrib time")
            }
            eshaadate.after(ft) -> {
                azanBinding.txtNextPrayTime.text = st_eshaa
                azanBinding.txtNextPrayName.text = "العشاء"
                Log.d("comparing", "eshaa time")
            }
        }
    }

    private fun startService(
        st_fajr: String,
        st_zohr: String,
        st_asr: String,
        st_maghrib: String,
        st_eshaa: String
    ) {
        Log.d("service_starts ", "success")
        val i = Intent(this.context, AzanNotifBroadCast::class.java)
        i.putExtra("fajr_time", "$st_fajr")
        i.putExtra("zohr_time", "$st_zohr")
        i.putExtra("asr_time", "$st_asr")
        i.putExtra("maghrib_time", "$st_maghrib")
        i.putExtra("eshaa_time", "$st_eshaa")
        val pendingIntent = PendingIntent.getBroadcast(this.requireContext(), 0, i, 0)

       val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val onMinuteInMills = (1000 * 60).toLong()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 1)
        calendar[Calendar.SECOND] = 0
        val triggerAt = calendar.timeInMillis

        alarmManager.cancel(pendingIntent)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAt,
            onMinuteInMills,
            pendingIntent
        )
    }

    private fun handleNetworkChanges() {
        NetworkAbility.getNetworkLiveData(this.requireContext()).observe(viewLifecycleOwner, Observer { isConnected ->
            if (!isConnected) {
                azanBinding.isConnected =false
                azanBinding.txtNetworkStatus.text = getString(R.string.text_no_connectivity)
                azanBinding.relConnectionErr.setBackgroundColor(resources.getColor(R.color.colorStatusNotConnected))
            }else{
                Log.d("statusNetwork", "connected!!")
                CoroutineScope(Dispatchers.Main).launch {
                    getAndStore("zagazig")

                    azanBinding.txtNetworkStatus.text = getString(R.string.text_connectivity)
                    azanBinding.relConnectionErr.apply {
                        setBackgroundColor(resources.getColor(R.color.colorStatusConnected))

                        animate()
                            .alpha(1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    /*
                                    * Execute Any Function After Connection Work
                                    * */
                                }
                            })
                    }
                    delay(2000)
                    azanBinding.isConnected = true
                }
            }
        })
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }
}