package com.brandoncano.inductancecalculator.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Colorize
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.navigation.Screen
import com.brandoncano.inductancecalculator.ui.MainActivity
import com.brandoncano.inductancecalculator.ui.composables.AppComponentPreviews
import com.brandoncano.inductancecalculator.ui.composables.ArrowButtonCard
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme
import com.brandoncano.inductancecalculator.ui.theme.textStyleHeadline
import com.brandoncano.inductancecalculator.util.OpenLink

@Composable
fun AppIcon() {
    val backgroundColor = if (isSystemInDarkTheme()) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primary
    }
    Card(
        modifier = Modifier
            .padding(top = 16.dp)
            .size(128.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = stringResource(id = R.string.content_description_app_icon),
                modifier = Modifier.size(128.dp)
            )
        }
    }
}

@Composable
fun AppCalculatorButtons(navController: NavController) {
    Column {
        Text(
            text = stringResource(id = R.string.home_calculators_header_text),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .align(Alignment.Start),
            style = textStyleHeadline(),
        )
        ArrowButtonCard(
            listOf(
                Icons.Outlined.Colorize,
                Icons.Outlined.Search
            ),
            listOf(
                stringResource(id = R.string.home_button_color_to_value),
                stringResource(id = R.string.home_button_value_to_color)
            ),
            listOf(
                { navController.navigate(Screen.ColorToValue.route) },
                { navController.navigate(Screen.ValueToColor.route) }
            ),
        )
        // TODO
//        ArrowButtonCard(
//            Icons.Outlined.WidthFull,
//            stringResource(id = R.string.home_button_smd),
//        ) {
//            navController.navigate(Screen.Smd.route)
//        }
    }
}

@Composable
fun OurAppsButtons(context: Context) {
    Column {
        Text(
            text = stringResource(id = R.string.home_our_apps_header_text),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .align(Alignment.Start),
            style = textStyleHeadline(),
        )
        ArrowButtonCard(
            Icons.Outlined.Grade,
            stringResource(id = R.string.home_button_rate_us),
        ) {
            OpenLink.openInductorApp(context)
        }
        ArrowButtonCard(
            listOf(
                // Note: we do this instead because material icons does not have the outlined version
                ImageVector.vectorResource(id = R.drawable.icon_outline_add_to_home_screen),
                ImageVector.vectorResource(id = R.drawable.icon_outline_add_to_home_screen)
            ),
            listOf(
                stringResource(id = R.string.home_button_view_resistor_app),
                stringResource(id = R.string.home_button_view_capacitor_app)
            ),
            listOf(
                { OpenLink.openResistorApp(context) },
                { OpenLink.openCapacitorApp(context) }
            ),
        )
    }
}

@AppComponentPreviews
@Composable
private fun AppIconPreview() {
    InductanceCalculatorTheme {
        AppIcon()
    }
}

@AppComponentPreviews
@Composable
private fun StandardCalculatorButtonsPreview() {
    InductanceCalculatorTheme {
        val app = MainActivity()
        AppCalculatorButtons(NavController(app))
    }
}

@AppComponentPreviews
@Composable
private fun OurAppsButtonsPreview() {
    InductanceCalculatorTheme {
        val app = MainActivity()
        OurAppsButtons(app)
    }
}