package com.mahmoudbashir.azan_app.di.module

import android.app.Application
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mahmoudbashir.azan_app.AzanApplication
import com.mahmoudbashir.azan_app.room.AzanTimesDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class AzanModule(app:Application) {
    private var azanApplication = app
    private lateinit var azanDb:AzanTimesDatabase
    private val databaseCallback = object :RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomDatabaseModule","onCreate")
        }
    }

    @Singleton
    @Provides
    fun providesRoomDatabase():AzanTimesDatabase{
        azanDb = Room.databaseBuilder(azanApplication,AzanTimesDatabase::class.java,"\"Azan_db\"")
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()

        return azanDb
    }

    @Singleton
    @Provides
    fun providesDao(db:AzanTimesDatabase) = db.dao()
}