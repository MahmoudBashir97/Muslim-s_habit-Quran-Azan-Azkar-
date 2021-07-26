package com.mahmoudbashir.azan_app.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.databinding.FragmentAddInfoBinding
import com.mahmoudbashir.azan_app.extentions.NetworkAbility
import com.mahmoudbashir.azan_app.local.model.residents.Residents_Model
import com.mahmoudbashir.azan_app.local.model.residents.data
import com.mahmoudbashir.azan_app.locationTracker.GpsTracker
import com.mahmoudbashir.azan_app.locationTracker.GpsTracker_Location
import com.mahmoudbashir.azan_app.pojo.results
import com.mahmoudbashir.azan_app.ui.MainActivity
import com.mahmoudbashir.azan_app.viewmodel.AzanViewModel
import com.mahmoudbashir.prefs.SharedPreference
import kotlinx.coroutines.*
import java.io.IOException
import java.io.InputStream


class Add_Info_Fragment : Fragment() ,AdapterView.OnItemSelectedListener{
    private val str = arrayOf(
        "اختر طريقة الحساب",
        "مذهب الشيعة الاثنا عشرية",
        "جامعة العلوم الإسلامية في كيراتشي",
        "الجمعية الإسلامية لأمريكا الشمالية",
        "رابطة العالم الإسلامي",
        "جامعة أم القرى في مكة المكرمة",
        "الهيئة المصرية العامة للمساحة",
        "معهد الجيوفيزياء في جامعة طهران",
        "المغرب",
        "دائرة النهوض الإسلامي ، ماليزيا (JAKIM)",
        "مجلس أوغاما إسلام سنغافورة",
        "اتحاد المنظمات الإسلامية بفرنسا",
        "تركيا"
    )
    lateinit var addBinding: FragmentAddInfoBinding
    lateinit var adapter: ArrayAdapter<*>
    lateinit var residentsModel: Residents_Model
    lateinit var listResidents:ArrayList<data>
    lateinit var countries_list:ArrayList<String>
    lateinit var cities_list:ArrayList<String>
    lateinit var selected_det_way:String
    lateinit var selected_country:String
    var selected_city:String="ccc"
    private var school_Id:Int=-1
    private lateinit var viewModel: AzanViewModel

    var locationManager: LocationManager? = null
    var GpsStatus = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    var lat=0.0
    var lng  =0.0

    lateinit var tracker:GpsTracker_Location


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (SharedPreference.getInastance(this.requireContext()).isLoggedIn &&
                SharedPreference.getInastance(this.requireContext()).path.equals("splash")){
            findNavController().navigate(Add_Info_FragmentDirections.actionAddInfoFragmentToHomeFragment())
        }

        // Inflate the layout for this fragment
        addBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add__info_,
            container,
            false
        )
        //loading local json data
        getResidentsDataFromJson()
        addBinding.spinSelectWay.setSelection(0)
        adapter= ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, str)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        addBinding.spinSelectWay.adapter = adapter
        addBinding.spinSelectWay.onItemSelectedListener = this
        countriesSpin()
        viewModel = (activity as MainActivity).viewModel


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    lat = location.latitude
                    lng = location.longitude
                }
            }
        }

        handleNetworkChanges()
        saveRequiredData()
        getLocation()
        //checkGpsStatus()

            return addBinding.root
    }

    fun getLocation() {
        tracker = GpsTracker_Location(requireContext())
        if (tracker.canGetLocation()) {
            val latitude: Double = tracker.getLatitude()
            val longitude: Double = tracker.getLongitude()
            lat = tracker.getLatitude()
            lng = tracker.getLongitude()
        } else {
            tracker.showSettingsAlert()
        }
    }


/*    private fun gpsTurnedOn(){
            val d = AlertDialog.Builder(context)
                .setTitle("Enable GPS!")
                .setMessage("Please Enable GPS for setting data up")
                .setPositiveButton("Enable"){ dialog, _->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context?.startActivity(intent);
                    dialog.dismiss()

                }.setNegativeButton("Cancel"){ dialog, _->
                    dialog.dismiss()
                }
            d.show()
    }

    private fun checkGpsStatus() {
        GpsStatus = false
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        assert(locationManager != null)
        GpsStatus = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (GpsStatus) {


            *//*  if (ActivityCompat.checkSelfPermission(
                      requireContext(),
                      Manifest.permission.ACCESS_FINE_LOCATION
                  ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                      requireContext(),
                      Manifest.permission.ACCESS_COARSE_LOCATION
                  ) != PackageManager.PERMISSION_GRANTED
              ) {
                  return
              }

              fusedLocationClient.lastLocation
                  .addOnSuccessListener { location:Location? ->
                      val lat = location?.latitude
                      val lng = location?.longitude
                      Toast.makeText(context,"mmmGPS Is Enabled : $lat , __ $lng" ,Toast.LENGTH_LONG).show()
                  }*//*

        } else {
            gpsTurnedOn()
            Toast.makeText(context, "GPS Is Disabled", Toast.LENGTH_LONG).show()
        }
    }*/

    override fun onResume() {
        super.onResume()
        if (GpsStatus) {
            Toast.makeText(context, "GPS Is Enabled", Toast.LENGTH_LONG).show()
        }
      //  if (requestingLocationUpdates) startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
  /*  private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }*/

    private fun saveRequiredData(){
        addBinding.btnSave.setOnClickListener{
            if (school_Id> -1 ){

                    addBinding.isSaved = true
                    getAndStore(selected_city, school_Id)
            }else{
                Toast.makeText(context, "من فضلك اختر مدينتك وطريقة الحساب.", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position>0){
            selected_det_way = str[position]
            when(position){
                1 -> {
                    school_Id = 0
                }
                2 -> {
                    school_Id = 1
                }
                3 -> {
                    school_Id = 2
                }
                4 -> {
                    school_Id = 3
                }
                5 -> {
                    school_Id = 4
                }
                6 -> {
                    school_Id = 5
                }
                7 -> {
                    school_Id = 7
                }
                8 -> {
                    school_Id = 8
                }
                9 -> {
                    school_Id = 9
                }
                10 -> {
                    school_Id = 10
                }
                11 -> {
                    school_Id = 11
                }
                12 -> {
                    school_Id = 12
                }

            }
            Log.d("school_id ", "$school_Id")
            //SharedPreference.getInastance(this.requireContext()).save_InfoData("zagazig",school_Id)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun countriesSpin(){
        adapter= ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_list_item_1,
            countries_list
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        addBinding.spinSelectCountry.adapter = adapter
        addBinding.spinSelectCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               /* if (position>0){
                    citiesSpin(position)
                   // Toast.makeText(context,countries_list[position],Toast.LENGTH_LONG).show()
                }*/
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


   /* private fun citiesSpin(position: Int){

        cities_list = listResidents[position].cities as ArrayList<String>
        adapter= ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_list_item_1,
            cities_list
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        addBinding.spinSelectCity.adapter = adapter
        addBinding.spinSelectCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected_city = cities_list[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }*/
    private fun getResidentsDataFromJson(){
        cities_list = ArrayList()
        listResidents = ArrayList()
        countries_list = ArrayList()
       // cities_list = ArrayList()
        val i: InputStream? = this.activity?.assets?.open("residents_data.json")
        if (i != null) {
            inputStreamToString(i)
        }
        val myJson =
            activity?.resources?.let { inputStreamToString(it.openRawResource(R.raw.residents_data)) }
        residentsModel = Gson().fromJson(myJson, Residents_Model::class.java)

        listResidents.clear()
        countries_list.clear()
        cities_list.clear()

        listResidents.addAll(residentsModel.jsOn.data)
        for (elements in residentsModel.jsOn.data){
            countries_list.add(elements.country)
        }
    }
    private fun inputStreamToString(inputStream: InputStream): String? {
        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            null
        }
    }

    private fun getAndStore(city: String, schoolId: Int) {
        getLocation()
        Log.d("insert_status", "$lat , mm $lng")
        GlobalScope.launch(Dispatchers.Main) {
            if (lat>0.0){
                if (viewModel.getAzanTimes("$lat","$lng",333, 1, schoolId).isSuccessful) {

                    viewModel.getAzanTimes("$lat","$lng",333, 1, schoolId).body()?.let {

                        Log.d("timesAzan: ", "result: ${it.results.location.city}")
                        val result: results = it.results
                        //viewModel.insert(result)
                        val check = viewModel.insert(result).isActive.and(isAdded)

                        if (check)
                        {
                            addBinding.isSaved = false

                            findNavController().navigate(Add_Info_FragmentDirections.actionAddInfoFragmentToHomeFragment())
                            SharedPreference.getInastance(context).save_InfoData(
                                selected_city,
                                school_Id,
                                "$lat",
                                "$lng"
                            )
                        }
                    }
                            Log.d("timesAzan: ", "Worked success")
                }
            }else{
                Toast.makeText(context,"من فضلك قم بتفعيل ال GPS الخاص بك !",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun handleNetworkChanges() {
        NetworkAbility.getNetworkLiveData(this.requireContext()).observe(
            viewLifecycleOwner,
            Observer { isConnected ->
                if (!isConnected) {
                    addBinding.isConnected = false

                    addBinding.txtNetworkStatus.text = getString(R.string.text_no_connectivity)
                    addBinding.relConnectionErr.setBackgroundColor(resources.getColor(R.color.colorStatusNotConnected))
                } else {
                    Log.d("statusNetwork", "connected!!")
                    CoroutineScope(Dispatchers.Main).launch {
                        // getAndStore(selected_city)

                        addBinding.txtNetworkStatus.text = getString(R.string.text_connectivity)
                        addBinding.relConnectionErr.apply {
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
                        addBinding.isConnected = true
                    }
                }
            })
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }
}