package com.brandoncano.inductancecalculator.model.ctv

data class InductorCtv(
    var band1: String = "",
    var band2: String = "",
    var band3: String = "",
    var band4: String = "",
) {
    fun isEmpty(): Boolean {
        return band1.isEmpty() || band2.isEmpty() || band4.isEmpty()
    }

    override fun toString(): String {
        return "[ $band1, $band2, $band3, $band4 ]"
    }
}
