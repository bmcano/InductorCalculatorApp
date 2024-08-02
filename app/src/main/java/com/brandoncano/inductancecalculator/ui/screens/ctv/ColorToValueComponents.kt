package com.brandoncano.inductancecalculator.ui.screens.ctv

import android.graphics.Picture
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.constants.Colors
import com.brandoncano.inductancecalculator.model.ctv.InductorCtv
import com.brandoncano.inductancecalculator.ui.composables.AppCard
import com.brandoncano.inductancecalculator.ui.composables.AppComponentPreviews
import com.brandoncano.inductancecalculator.ui.composables.DrawContent
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme
import com.brandoncano.inductancecalculator.ui.theme.textStyleTitle
import com.brandoncano.inductancecalculator.util.ColorFinder
import com.brandoncano.inductancecalculator.util.formatInductance

private data class ImageColorPair(@DrawableRes val drawableRes: Int, val color: String)

// picture stuff
@Composable
fun inductorPicture(inductor: InductorCtv): Picture {
    val picture = remember { Picture() }
    DrawContent(picture) {
        InductorLayout(inductor)
    }
    return picture
}

@Composable
fun InductorLayout(inductor: InductorCtv) {
    Column(
        modifier = Modifier.padding(top = 16.dp, start = 32.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InductorRow(
            ImageColorPair(R.drawable.img_inductor_wire, Colors.INDUCTOR_WIRE),
            ImageColorPair(R.drawable.img_inductor_end_left, Colors.INDUCTOR_GREEN),
            ImageColorPair(R.drawable.img_inductor_band_96, inductor.band1),
            ImageColorPair(R.drawable.img_inductor_curve_left, Colors.INDUCTOR_GREEN),
            ImageColorPair(R.drawable.img_inductor_band_64, inductor.band2),
            ImageColorPair(R.drawable.img_inductor_band_64_wide, Colors.INDUCTOR_GREEN),
            ImageColorPair(R.drawable.img_inductor_band_64, inductor.band3),
            ImageColorPair(R.drawable.img_inductor_curve_right, Colors.INDUCTOR_GREEN),
            ImageColorPair(R.drawable.img_inductor_band_96, inductor.band4),
            ImageColorPair(R.drawable.img_inductor_end_right, Colors.INDUCTOR_GREEN),
            ImageColorPair(R.drawable.img_inductor_wire, Colors.INDUCTOR_WIRE),
        )
        // TODO - inductance text
        val text = if (inductor.isEmpty()) {
            stringResource(id = R.string.default_ctv_value)
        } else {
            inductor.formatInductance()
        }
        InductanceText(text)
    }
}

@Composable
private fun InductorRow(vararg inductorImages: ImageColorPair) {
    Row(horizontalArrangement = Arrangement.Absolute.Center) {
        inductorImages.forEach { inductorImage ->
            val color = ColorFinder.textToColor(inductorImage.color)
            InductorImage(inductorImage.drawableRes, color)
        }
    }
}

@Composable
private fun InductorImage(@DrawableRes drawableRes: Int, color: Color) {
    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = null,
        colorFilter = ColorFilter.tint(color),
    )
}

@Composable
private fun InductanceText(inductance: String) {
    AppCard(modifier = Modifier.padding(top = 12.dp)) {
        Text(
            text = inductance,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 6.dp, bottom = 6.dp, start = 12.dp, end = 12.dp),
            style = textStyleTitle(),
        )
    }
}

@AppComponentPreviews
@Composable
private fun InductorLayoutsPreview() {
    InductanceCalculatorTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InductorLayout(InductorCtv())
        }
    }
}
