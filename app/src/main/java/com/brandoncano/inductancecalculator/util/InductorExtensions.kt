package com.brandoncano.inductancecalculator.util

import com.brandoncano.inductancecalculator.model.ctv.InductorCtv
import com.brandoncano.inductancecalculator.model.vtc.InductorVtc

fun InductorCtv.formatInductance(): String {
    return InductanceFormatter.execute(this)
}

fun InductorVtc.isInvalidInput(): Boolean {
    return !IsValidInductance.execute(this.inductance, this.units)
}

fun InductorVtc.formatInductor() {
    InductorFormatter.execute(this)
}

fun String.adjustValueForSharing(): String {
    val color = ColorFinder.textToColor(this)
    return ColorFinder.colorToColorText(color)
}
