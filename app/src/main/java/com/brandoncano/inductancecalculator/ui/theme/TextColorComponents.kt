package com.brandoncano.inductancecalculator.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.brandoncano.sharedcomponents.composables.AppComponentPreviews
import com.brandoncano.sharedcomponents.text.textStyleTitle

/**
 * Note: Any text colors we need to set manually are extension functions
 */

@Composable
fun TextStyle.white() = this.merge(
    color = white
)

@Composable
fun TextStyle.iconGray() = this.merge(
    color = MaterialTheme.colorScheme.onSurfaceVariant
)

@AppComponentPreviews
@Composable
private fun TextColorsPreview() {
    InductorCalculatorTheme {
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "White",
                style = textStyleTitle().white(),
            )
            Text(
                text = "Menu Text",
                style = textStyleTitle().iconGray(),
            )
        }
    }
}
