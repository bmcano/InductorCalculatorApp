package com.brandoncano.inductancecalculator.util

import androidx.compose.ui.graphics.Color
import com.brandoncano.inductancecalculator.ui.theme.black
import com.brandoncano.inductancecalculator.ui.theme.blue
import com.brandoncano.inductancecalculator.ui.theme.brown
import com.brandoncano.inductancecalculator.ui.theme.gold
import com.brandoncano.inductancecalculator.ui.theme.gray
import com.brandoncano.inductancecalculator.ui.theme.green
import com.brandoncano.inductancecalculator.ui.theme.inductor_green
import com.brandoncano.inductancecalculator.ui.theme.inductor_wire
import com.brandoncano.inductancecalculator.ui.theme.orange
import com.brandoncano.inductancecalculator.ui.theme.red
import com.brandoncano.inductancecalculator.ui.theme.silver
import com.brandoncano.inductancecalculator.ui.theme.violet
import com.brandoncano.inductancecalculator.ui.theme.white
import com.brandoncano.inductancecalculator.ui.theme.yellow
import com.brandoncano.inductancecalculator.constants.Colors as C

/**
 * Job: Find the correct color, either as a string or resource based on the input.
 * Note: "${S.PM}20%" -> resistor_beige -> hidden in else conditions.
 */
object ColorFinder {

    fun textToColor(text: String): Color {
        return when (text) {
            C.BLACK  -> black
            C.BROWN  -> brown
            C.RED    -> red
            C.ORANGE -> orange
            C.YELLOW -> yellow
            C.GREEN  -> green
            C.BLUE   -> blue
            C.VIOLET -> violet
            C.GRAY   -> gray
            C.WHITE  -> white
            C.GOLD   -> gold
            C.SILVER -> silver
            C.INDUCTOR_WIRE -> inductor_wire
            else     -> inductor_green
        }
    }
}
