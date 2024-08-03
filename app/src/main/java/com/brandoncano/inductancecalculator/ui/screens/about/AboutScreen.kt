package com.brandoncano.inductancecalculator.ui.screens.about

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.ui.MainActivity
import com.brandoncano.inductancecalculator.ui.composables.AppScreenPreviews
import com.brandoncano.inductancecalculator.ui.composables.AppTopAppBar
import com.brandoncano.inductancecalculator.ui.screens.OurAppsButtons
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme

@Composable
fun AboutScreen(context: Context) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ContentView(context)
    }
}

@Composable
private fun ContentView(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        AppTopAppBar(stringResource(R.string.about_title))
        AuthorCard()
        AppInfoCard()
        ViewPrivacyPolicy(context)
        DescriptionCard()
        OurAppsButtons(context)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@AppScreenPreviews
@Composable
private fun AboutPreview() {
    val app = MainActivity()
    InductanceCalculatorTheme {
        AboutScreen(app)
    }
}