package com.mahmoudbashir.azan_app.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.databinding.FragmentAddInfoBinding
import com.mahmoudbashir.azan_app.extentions.NetworkAbility
import com.mahmoudbashir.azan_app.local.model.residents.Residents_Model
import com.mahmoudbashir.azan_app.local.model.residents.data
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
    lateinit var selected_city:String
    private var school_Id:Int=-1
    private lateinit var viewModel: AzanViewModel
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


        handleNetworkChanges()
        saveRequiredData()

            return addBinding.root
    }


    private fun saveRequiredData(){
        addBinding.btnSave.setOnClickListener{
            if (school_Id>-1 ){

                    addBinding.isSaved = true
                    getAndStore(selected_city)
            }else{
                Toast.makeText(context,"من فضلك اختر مدينتك وطريقة الحساب.",Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position>0){
            selected_det_way = str[position]
            when(position){
                1 ->{
                    school_Id = 0
                }
                2 ->{
                    school_Id = 1
                }
                3 ->{
                    school_Id = 2
                }
                4 ->{
                    school_Id = 3
                }
                5 ->{
                    school_Id = 4
                }
                6 ->{
                    school_Id = 5
                }
                7 ->{
                    school_Id = 7
                }
                8 ->{
                    school_Id = 8
                }
                9 ->{
                    school_Id = 9
                }
                10 ->{
                    school_Id = 10
                }
                11 ->{
                    school_Id = 11
                }
                12 ->{
                    school_Id = 12
                }

            }
            Log.d("school_id ","$school_Id")
            //SharedPreference.getInastance(this.requireContext()).save_InfoData("zagazig",school_Id)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun countriesSpin(){
        adapter= ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, countries_list)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown)
        addBinding.spinSelectCountry.adapter = adapter
        addBinding.spinSelectCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position>0){
                    citiesSpin(position)
                   // Toast.makeText(context,countries_list[position],Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    private fun citiesSpin(position: Int){

        cities_list = listResidents[position].cities as ArrayList<String>
        adapter= ArrayAdapter(this.requireContext(), android.R.layout.simple_list_item_1, cities_list)
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
    }
    private fun getResidentsDataFromJson(){
        listResidents = ArrayList()
        countries_list = ArrayList()
        cities_list = ArrayList()
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

    private fun getAndStore(city: String) {
        Log.d("insert_status","$city")
        GlobalScope.launch(Dispatchers.Main) {
            if (viewModel.getAzanTimes(city, 1).isSuccessful) {
                viewModel.getAzanTimes(city, 1).body()?.let {
                    Log.d("timesAzan: ", "result: ${it.results.location.city}")
                    val result: results = it.results
                    //viewModel.insert(result)
                    val check = viewModel.insert(result).isActive.and(isAdded)

                        if (check)
                        {
                            addBinding.isSaved = false

                            findNavController().navigate(Add_Info_FragmentDirections.actionAddInfoFragmentToHomeFragment())
                            SharedPreference.getInastance(context).save_InfoData(selected_city,school_Id)
                        }
                }
                Log.d("timesAzan: ", "Worked success")
            }
        }
    }

    private fun handleNetworkChanges() {
        NetworkAbility.getNetworkLiveData(this.requireContext()).observe(viewLifecycleOwner, Observer { isConnected ->
            if (!isConnected) {
                addBinding.isConnected =false

                addBinding.txtNetworkStatus.text = getString(R.string.text_no_connectivity)
                addBinding.relConnectionErr.setBackgroundColor(resources.getColor(R.color.colorStatusNotConnected))
            }else{
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