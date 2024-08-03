package com.brandoncano.inductancecalculator.model.vtc

import com.brandoncano.inductancecalculator.util.adjustValueForSharing

data class InductorVtc(
    val inductance: String = "",
    val units: String = "",
    val tolerance: String = "",
) {
    var band1 = ""
    var band2 = ""
    var band3 = ""

    fun getInductanceValue(): String {
        return "$inductance $units $tolerance"
    }

    fun isEmpty(): Boolean {
        return inductance.isEmpty() || units.isEmpty() || tolerance.isEmpty()
    }

    override fun toString(): String {
        val band4 = tolerance.adjustValueForSharing()
        return "[ $band1, $band2, $band3, $band4 ]"
    }
}
