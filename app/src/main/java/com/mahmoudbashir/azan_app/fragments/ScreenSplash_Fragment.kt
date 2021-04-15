package com.mahmoudbashir.azan_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.mahmoudbashir.azan_app.R
import com.mahmoudbashir.azan_app.databinding.FragmentScreenSplashBinding
import com.mahmoudbashir.prefs.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ScreenSplash_Fragment : Fragment() {

    lateinit var splashBinding:FragmentScreenSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        splashBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_screen_splash_, container, false)

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            findNavController().navigate(ScreenSplash_FragmentDirections.actionScreenSplashFragmentToAddInfoFragment())
            SharedPreference.getInastance(context).savePath("splash")
        }

        return splashBinding.root
    }

}