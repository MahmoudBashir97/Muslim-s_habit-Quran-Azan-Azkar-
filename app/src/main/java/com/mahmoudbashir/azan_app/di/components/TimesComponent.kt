package com.mahmoudbashir.azan_app.di.components

import com.mahmoudbashir.azan_app.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component()
interface TimesComponent {
    fun inject(activit: MainActivity)
}