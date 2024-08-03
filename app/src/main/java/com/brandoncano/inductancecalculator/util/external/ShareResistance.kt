package com.brandoncano.inductancecalculator.util.external

import android.content.Context
import android.content.Intent

/**
 * Job: This will take the supplied text make it shareable.
 */
object ShareResistance {

    fun execute(context: Context, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(intent, "Share via"))
    }
}
