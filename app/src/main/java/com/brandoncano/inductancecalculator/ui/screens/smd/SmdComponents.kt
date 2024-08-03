package com.brandoncano.inductancecalculator.ui.screens.smd

import android.graphics.Picture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.components.SmdTolerance
import com.brandoncano.inductancecalculator.model.smd.InductorSmd
import com.brandoncano.inductancecalculator.ui.composables.AppCard
import com.brandoncano.inductancecalculator.ui.composables.AppComponentPreviews
import com.brandoncano.inductancecalculator.ui.composables.DrawContent
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme
import com.brandoncano.inductancecalculator.ui.theme.textStyleLargeTitle
import com.brandoncano.inductancecalculator.ui.theme.textStyleTitle
import com.brandoncano.inductancecalculator.ui.theme.white
import com.brandoncano.inductancecalculator.util.formatInductance

@Composable
fun smdInductorPicture(inductor: InductorSmd, isError: Boolean): Picture {
    val picture = remember { Picture() }
    DrawContent(picture) {
        SmdInductorLayout(inductor, isError)
    }
    return picture
}

@Composable
fun SmdInductorLayout(
    inductor: InductorSmd,
    isError: Boolean,
) {
    Column(
        modifier = Modifier.padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.clip(RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_smd_inductor_outline),
                contentDescription = null,
            )
            Image(
                painter = painterResource(id = R.drawable.img_smd_inductor),
                contentDescription = stringResource(id = R.string.content_description_app_icon),
            )
            val text = if (isError) {
                stringResource(id = R.string.error_na)
            }  else {
                inductor.code
            }
            Text(
                text = text + inductor.tolerance,
                style = textStyleLargeTitle().white()
            )
        }
        val text = when {
            inductor.isEmpty() -> stringResource(id = R.string.default_smd_value)
            isError -> stringResource(id = R.string.error_na)
            else -> "${inductor.formatInductance()} ${SmdTolerance.getTolerance(inductor.tolerance)}".trimEnd()
        }
        InductanceText(text)
    }
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
private fun SmdInductorLayoutPreview() {
    InductanceCalculatorTheme {
        val inductor = InductorSmd(code = "1R4")
        SmdInductorLayout(inductor, false)
    }
}
