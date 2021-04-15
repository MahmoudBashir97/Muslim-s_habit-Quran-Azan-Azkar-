package com.mahmoudbashir.azan_app.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.mahmoudbashir.azan_app.AzanApplication
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.di.components.DaggerTimesComponent
import com.mahmoudbashir.azan_app.di.components.TimesComponent
import com.mahmoudbashir.azan_app.di.module.AzanModule
import com.mahmoudbashir.azan_app.repository.AzanRepository
import com.mahmoudbashir.azan_app.viewmodel.AzanViewModel
import com.mahmoudbashir.azan_app.viewmodel.ViewModelProviderFactory


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: AzanViewModel
    private var currentPray: MutableLiveData<String> = MutableLiveData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
        window.statusBarColor = Color.WHITE
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        injectDagger()
        setUpViewModel()
    }


    private fun setUpViewModel() {
        val repo = AzanRepository(AzanModule(this.application).providesRoomDatabase())
        val viewModelProviderFactory = ViewModelProviderFactory(application, repo)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(AzanViewModel::class.java)
    }





    private fun injectDagger() {
        AzanApplication.instance.timeComp.inject(this)

        val comp: TimesComponent = DaggerTimesComponent.create()
        comp.inject(this)
    }
}