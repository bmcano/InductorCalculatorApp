package com.brandoncano.inductancecalculator.util

import com.brandoncano.inductancecalculator.model.ctv.InductorCtv

fun InductorCtv.formatInductance(): String {
    return InductanceFormatter.execute(this)
}

