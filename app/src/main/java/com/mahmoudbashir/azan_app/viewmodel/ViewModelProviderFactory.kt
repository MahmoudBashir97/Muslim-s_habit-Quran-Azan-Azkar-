@file:Suppress("UNCHECKED_CAST")

package com.mahmoudbashir.azan_app.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.azan_app.AzanApplication
import com.mahmoudbashir.azan_app.repository.AzanRepository
import javax.inject.Inject

class ViewModelProviderFactory @Inject constructor(
    private val app: Application,
    private val repos: AzanRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AzanViewModel(app, repos) as T
    }
}