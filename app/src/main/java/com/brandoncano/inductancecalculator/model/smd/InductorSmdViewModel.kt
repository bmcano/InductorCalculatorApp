package com.brandoncano.inductancecalculator.model.smd

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InductorSmdViewModel(context: Context): ViewModel() {

    private val repository = InductorSmdRepository.getInstance(context)
    private var inductor = MutableLiveData<InductorSmd>()

    init {
        inductor.value = InductorSmd()
    }

    override fun onCleared() {
        inductor.value = null
    }

    fun clear() {
        inductor.value = InductorSmd()
        repository.clear()
    }

    fun getInductorLiveData(): LiveData<InductorSmd> {
        inductor.value = repository.loadInductor()
        return inductor
    }

    fun updateValues(code: String, tolerance: String) {
        inductor.value = inductor.value?.copy(code = code, tolerance = tolerance)
    }

    fun saveInductorValues(inductor: InductorSmd) {
        repository.saveResistor(inductor)
    }
}
