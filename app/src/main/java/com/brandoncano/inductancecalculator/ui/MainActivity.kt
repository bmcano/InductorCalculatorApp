package com.brandoncano.inductancecalculator.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.brandoncano.inductancecalculator.navigation.Navigation
import com.brandoncano.inductancecalculator.ui.theme.InductorCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InductorCalculatorTheme {
                Navigation(this)
            }
        }
    }
}
