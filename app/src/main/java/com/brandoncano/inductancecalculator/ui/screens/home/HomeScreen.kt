package com.brandoncano.inductancecalculator.ui.screens.home

import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.ui.MainActivity
import com.brandoncano.inductancecalculator.ui.composables.AboutAppMenuItem
import com.brandoncano.inductancecalculator.ui.composables.AppMenuTopAppBar
import com.brandoncano.inductancecalculator.ui.composables.AppScreenPreviews
import com.brandoncano.inductancecalculator.ui.composables.FeedbackMenuItem
import com.brandoncano.inductancecalculator.ui.theme.InductorCalculatorTheme

@Composable
fun HomeScreen(context: Context, navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ContentView(context, navController)
    }
}

@Composable
private fun ContentView(context: Context, navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val showMenu = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppMenuTopAppBar(stringResource(R.string.app_name), interactionSource, showMenu) {
            FeedbackMenuItem(context, showMenu)
            AboutAppMenuItem(navController, showMenu)
        }
        AppIcon()
        AppCalculatorButtons(navController)
        OurAppsButtons(context)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@AppScreenPreviews
@Composable
private fun HomePreview() {
    val app = MainActivity()
    InductorCalculatorTheme {
        HomeScreen(app, NavController(app))
    }
}
