package com.mahmoudbashir.azan_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mahmoudbashir.azan_app.pojo.results

@Database(entities = [results::class],version = 1,exportSchema = false)
@TypeConverters(Converters::class)
abstract class AzanTimesDatabase :RoomDatabase(){
    abstract fun dao():AzanDbDao

    /*companion object{
        @Volatile
        private var instance:AzanTimesDatabase?=null
        private val LOCK = Any()

        operator fun invoke(context:Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also { instance=it }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AzanTimesDatabase::class.java,
            "todoList_db"
        ).build()
    }*/
}