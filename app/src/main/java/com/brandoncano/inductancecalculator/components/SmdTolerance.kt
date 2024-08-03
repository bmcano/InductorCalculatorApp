package com.brandoncano.inductancecalculator.components

import com.brandoncano.inductancecalculator.constants.Symbols as S

enum class SmdTolerance(val letter: String, val tolerance: String) {
    F("F", "${S.PM}1%"),
    G("G", "${S.PM}2%"),
    J("J", "${S.PM}5%"),
    K("K", "${S.PM}10%"),
    M("M", "${S.PM}20%");

    companion object {
        fun getLetterList(): List<String> {
            return entries.map { it.letter }
        }

        fun getTolerance(letter: String): String {
            return entries.find { it.letter == letter }?.tolerance ?: ""
        }
    }
}
