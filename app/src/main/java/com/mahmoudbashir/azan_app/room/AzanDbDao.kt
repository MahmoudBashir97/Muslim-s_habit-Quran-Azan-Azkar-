package com.mahmoudbashir.azan_app.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahmoudbashir.azan_app.pojo.results

@Dao
interface AzanDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(result:results)

    @Query("SELECT * FROM azan_times ORDER BY id ASC")
    fun getAllDataTimesStored():LiveData<results>

    @Query("SELECT * FROM azan_times ORDER BY id ASC")
    fun getAllDataForbroadCast():results

}