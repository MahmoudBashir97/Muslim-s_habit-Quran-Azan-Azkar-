package com.mahmoudbashir.azan_app.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.databinding.FragmentScreenSplashBinding
import com.mahmoudbashir.azan_app.ui.MainActivity
import com.mahmoudbashir.prefs.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ScreenSplash_Fragment : AppCompatActivity() {

    lateinit var splashBinding:FragmentScreenSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = DataBindingUtil.setContentView(this,
            R.layout.fragment_screen_splash_)
        window.statusBarColor = Color.WHITE


        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            val intent = Intent(this@ScreenSplash_Fragment,MainActivity::class.java)
                startActivity(intent)
            //findNavController(splashBinding.root.rootView.id).navigate(ScreenSplash_FragmentDirections.actionScreenSplashFragmentToAddInfoFragment())
            SharedPreference.getInastance(this@ScreenSplash_Fragment).savePath("splash")
        }
    }

}