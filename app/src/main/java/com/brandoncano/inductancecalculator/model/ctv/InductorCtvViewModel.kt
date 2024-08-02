package com.brandoncano.inductancecalculator.model.ctv

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InductorCtvViewModel(context: Context): ViewModel() {

    private val repository = InductorCtvRepository.getInstance(context)
    private var inductor = MutableLiveData<InductorCtv>().also {
        it.value = InductorCtv() // TODO - testing this over init
    }

    override fun onCleared() {
        inductor.value = null
    }

    fun clear() {
        inductor.value = InductorCtv()
        repository.clear()
    }

    fun getInductorLiveData(): LiveData<InductorCtv> {
        inductor.value = repository.loadInductor()
        return inductor
    }

    fun updateBands(b1: String, b2: String, b3: String, b4: String) {
        inductor.value = inductor.value?.copy(band1 = b1, band2 = b2, band3 = b3, band4 = b4)
    }

    fun saveInductorColors(inductor: InductorCtv) {
        repository.saveInductor(inductor)
    }
}
