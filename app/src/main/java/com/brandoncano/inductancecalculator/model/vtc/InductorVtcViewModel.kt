package com.brandoncano.inductancecalculator.model.vtc

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InductorVtcViewModel(context: Context): ViewModel() {

    private val repository = InductorVtcRepository.getInstance(context)
    private var inductor = MutableLiveData<InductorVtc>().also {
        it.value = InductorVtc()
    }

    override fun onCleared() {
        inductor.value = null
    }

    fun clear() {
        inductor.value = InductorVtc()
        repository.clear()
    }

    fun getInductorLiveData(): LiveData<InductorVtc> {
        inductor.value = repository.loadInductor()
        return inductor
    }

    fun updateValues(val1: String, val2: String, val3: String) {
        inductor.value = inductor.value?.copy(inductance = val1, units = val2, tolerance = val3)
    }

    fun saveInductorValues(inductor: InductorVtc) {
        repository.saveInductor(inductor)
    }
}
