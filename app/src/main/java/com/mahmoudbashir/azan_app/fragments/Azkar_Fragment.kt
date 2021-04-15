package com.mahmoudbashir.azan_app.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
    private lateinit var adapter:Azkar_adapter
    lateinit var rec_azkar:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v =  inflater.inflate(R.layout.fragment_azkar_, container, false)
        rec_azkar = v.findViewById(R.id.rec_azkar)
        rec_azkar.setHasFixedSize(true)

        getAzkarJsonData()

        adapter = Azkar_adapter(mlist)
        rec_azkar.adapter = adapter

        return v
    }

    private fun getAzkarJsonData() {
        mlist = ArrayList()
        val i: InputStream? = this.activity?.assets?.open("azkar.json")
        if (i != null) {
            inputStreamToString(i)
        }
        val myJson =
            activity?.resources?.let { inputStreamToString(it.openRawResource(R.raw.azkar)) }
        azkar = Gson().fromJson(myJson, Azkar_model::class.java)
        Log.d("json_size ", azkar.list[0].reference)
        mlist.addAll(azkar.list)
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