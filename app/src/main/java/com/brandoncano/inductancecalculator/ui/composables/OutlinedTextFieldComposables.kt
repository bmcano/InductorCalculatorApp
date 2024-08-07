package com.brandoncano.inductancecalculator.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.ui.theme.InductorCalculatorTheme
import com.brandoncano.inductancecalculator.ui.theme.textStyleSubhead

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    text: String = "",
    reset: Boolean = false,
    isError: Boolean = false,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    onOptionSelected: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    LaunchedEffect(reset) {
        if (reset) {
            selectedText = ""
        }
    }
    Column(Modifier.padding(start = 32.dp, end = 32.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
                onOptionSelected(it)
            },
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() }
                .clickable(interactionSource, null, enabled = true) { expanded = !expanded },
            label = { Text(stringResource(label)) },
            trailingIcon = {
                if (isError)
                    Image(
                       imageVector = Icons.Outlined.Error,
                       contentDescription = stringResource(id = R.string.content_description_error),
                       colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
                    )
            },
            isError = isError,
            supportingText = if (isError && errorMessage.isNotEmpty()) {
                { Text(text = errorMessage, style = textStyleSubhead()) }
            } else {
                null
            },
            keyboardOptions = keyboardOptions,
            singleLine = true,
            interactionSource = interactionSource
        )
    }
}

@AppComponentPreviews
@Composable
private fun AppTextFieldPreview() {
    InductorCalculatorTheme {
        Column {
            AppTextField(label = R.string.hint_units) { }
            AppTextField(label = R.string.hint_units, text = "Example") { }
            AppTextField(label = R.string.hint_units, isError = true) { }
            AppTextField(label = R.string.hint_units, isError = true, errorMessage = "error") { }
        }
    }
}
