package com.mahmoudbashir.azan_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.adapters.Azkar_adapter
import com.mahmoudbashir.azan_app.local.model.Azkar_model
import com.mahmoudbashir.azan_app.local.model.data
import com.mahmoudbashir.azan_app.local.model.residents.Residents_Model
import java.io.IOException
import java.io.InputStream


class Azkar_Fragment : Fragment() {

    lateinit var gson: Gson
    lateinit var azkar: Azkar_model
    lateinit var mlist: ArrayList<data>
    private lateinit var adapter: Azkar_adapter
    lateinit var night_azkar_btn: Button
    lateinit var mourning_azkar_btn: Button
    lateinit var rec_azkar: RecyclerView
    lateinit var lin_azkar: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_azkar_, container, false)
        rec_azkar = v.findViewById(R.id.rec_azkar)
        mourning_azkar_btn = v.findViewById(R.id.mourning_azkar_btn)
        night_azkar_btn = v.findViewById(R.id.night_azkar_btn)
        lin_azkar = v.findViewById(R.id.lin_azkar)
        rec_azkar.setHasFixedSize(true)

        toMourningAzkar()
        toNightAzkar()

        return v
    }

    private fun toMourningAzkar() {
        mourning_azkar_btn.setOnClickListener{
            rec_azkar.visibility=View.VISIBLE
            lin_azkar.visibility=View.GONE
            getAzkarJsonData("mourning")
        }
    }

    private fun toNightAzkar() {
        night_azkar_btn.setOnClickListener {
            rec_azkar.visibility=View.VISIBLE
            lin_azkar.visibility=View.GONE
            getAzkarJsonData("night")
        }
    }

    private fun getAzkarJsonData(sort: String) {
        mlist = ArrayList()
        val i: InputStream? = this.activity?.assets?.open("azkar.json")
        if (i != null) {
            inputStreamToString(i)
        }
        val myJson =
            activity?.resources?.let { inputStreamToString(it.openRawResource(R.raw.azkar)) }
        azkar = Gson().fromJson(myJson, Azkar_model::class.java)
        Log.d("json_size ", azkar.list[0].reference)

            for (elem in azkar.list) {
                if (sort.equals("mourning")) {
                if (elem.category.equals("أذكار الصباح")) {
                    mlist.add(elem)
                }
            } else if (elem.category.equals("أذكار المساء")){
                    mlist.add(elem)
                }
            }

        adapter = Azkar_adapter(mlist)
        rec_azkar.adapter = adapter
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
}