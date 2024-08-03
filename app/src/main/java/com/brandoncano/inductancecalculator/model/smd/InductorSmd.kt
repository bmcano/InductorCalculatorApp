package com.brandoncano.inductancecalculator.model.smd

data class InductorSmd(
    var code: String = "",
    var tolerance: String = "",
) {
    fun isEmpty(): Boolean {
        return code.length < 3
    }

//    override fun toString(): String {
//        val resistance = this.formatResistance()
//        return "Code: $code\nResistance: $resistance"
//    }
}