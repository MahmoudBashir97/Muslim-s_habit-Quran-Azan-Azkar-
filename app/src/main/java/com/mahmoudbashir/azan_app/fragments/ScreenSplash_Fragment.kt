package com.mahmoudbashir.azan_app.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        navToPowerManager()

        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            val intent = Intent(this@ScreenSplash_Fragment,MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            //findNavController(splashBinding.root.rootView.id).navigate(ScreenSplash_FragmentDirections.actionScreenSplashFragmentToAddInfoFragment())
            SharedPreference.getInastance(this@ScreenSplash_Fragment).savePath("splash")
        }
    }

    private fun navToPowerManager() {
        val powerManager = applicationContext.getSystemService(POWER_SERVICE) as PowerManager
        val packageName = "com.mahmoudbashir.azan_app"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val i = Intent()
            if (!powerManager.isIgnoringBatteryOptimizations(packageName)) {
                i.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                i.data = Uri.parse("package:$packageName")
                startActivity(i)
                Toast.makeText(applicationContext,"please check on no restrictions on this app for Battery saver"+"\n"+"???? ???????? ???? ???????????? ???? ?????????? ???? ?????????????? ???? ????????  ?????? ???????????????? ",800000f.toInt()).show()
            } else {
                i.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                i.data = Uri.parse("package:$packageName")
                startActivity(i)
            }
        }
    }

}