package com.mahmoudbashir.azan_app.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "azan_times")
data class results(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("location")
    val location: location,
    @SerializedName("settings")
    val settings: settings,
    @SerializedName("datetime")
    val datetime: List<datetime>
)