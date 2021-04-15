package com.mahmoudbashir.azan_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.local.model.data

class Azkar_adapter(private val mlist:ArrayList<data>): RecyclerView.Adapter<Azkar_adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val txt_category:TextView = itemView.findViewById<TextView>(R.id.txt_category)
         val txt_reference:TextView = itemView.findViewById<TextView>(R.id.txt_reference)
         val txt_zekr:TextView = itemView.findViewById<TextView>(R.id.txt_zekr)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Azkar_adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_item_azar,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Azkar_adapter.ViewHolder, position: Int) {
        holder.txt_category.text = mlist[position].category
        holder.txt_reference.text = mlist[position].reference
        holder.txt_zekr.text = mlist[position].zekr
    }

    override fun getItemCount(): Int {
        return mlist.size
    }
}