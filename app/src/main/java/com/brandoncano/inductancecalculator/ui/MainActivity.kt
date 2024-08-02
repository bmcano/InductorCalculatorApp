package com.brandoncano.inductancecalculator.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brandoncano.inductancecalculator.navigation.Navigation
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // TODO - check this out to see how it works for adding it to other apps
        setContent {
            InductanceCalculatorTheme {
                Navigation(this)
            }
        }
    }
}
