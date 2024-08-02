package com.brandoncano.inductancecalculator.model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brandoncano.inductancecalculator.model.ctv.InductorCtvViewModel

class InductorViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass.canonicalName) {
            InductorCtvViewModel::class.java.canonicalName -> InductorCtvViewModel(context) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}
