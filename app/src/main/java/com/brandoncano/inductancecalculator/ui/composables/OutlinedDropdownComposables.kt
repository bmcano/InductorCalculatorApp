package com.brandoncano.inductancecalculator.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.components.DropdownItem
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme
import com.brandoncano.inductancecalculator.ui.theme.RoundedSquare
import com.brandoncano.inductancecalculator.ui.theme.inductor_green
import com.brandoncano.inductancecalculator.ui.theme.textStyleBody
import com.brandoncano.inductancecalculator.ui.theme.textStyleCaption
import com.brandoncano.inductancecalculator.ui.theme.textStyleSubhead
import com.brandoncano.inductancecalculator.util.ColorFinder

@Composable
fun AppDropDownMenu(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    selectedOption: String = "",
    items: List<String>,
    reset: Boolean = false,
    onOptionSelected: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                expanded = !expanded
            }
        }
    }
    LaunchedEffect(reset) {
        if (reset) {
            selectedText = ""
        }
    }
    Column(Modifier.padding(start = 32.dp, end = 32.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() }
                .clickable(interactionSource, null, enabled = true) { expanded = !expanded },
            label = { Text(stringResource(label)) },
            trailingIcon = {
                val description = if (expanded) {
                    R.string.content_description_collapse
                } else {
                    R.string.content_description_expand
                }
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = description),
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            interactionSource = interactionSource
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .clickable(interactionSource, null, enabled = true) { expanded = !expanded }
        ) {
            items.forEach {
                TextDropDownItemView(it) {
                    selectedText = it
                    expanded = false
                    onOptionSelected(it)
                }
            }
        }
    }
}

@Composable
private fun TextDropDownItemView(item: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column {
            Text(
                text = item,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
                style = textStyleBody()
            )
        }
    }
}

@Composable
fun ImageTextDropDownMenu(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    selectedOption: String = "",
    items: List<DropdownItem>,
    reset: Boolean = false,
    isVtC: Boolean = false,
    onOptionSelected: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedOption) }
    var selectedLeadingIcon by remember {
        val color = ColorFinder.textToColor(selectedOption)
        mutableStateOf(color)
    }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                expanded = !expanded
            }
        }
    }
    LaunchedEffect(reset) {
        if (reset) {
            selectedText = ""
            selectedLeadingIcon = inductor_green
        }
    }
    Column(Modifier.padding(start = 32.dp, end = 32.dp)) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates -> textFieldSize = coordinates.size.toSize() }
                .clickable(interactionSource, null, enabled = true) { expanded = !expanded },
            label = { Text(stringResource(label)) },
            leadingIcon = if (selectedLeadingIcon != inductor_green) {
                { RoundedSquare(color = selectedLeadingIcon, size = 24.dp) }
            } else null,

            trailingIcon = {
                val description = if (expanded) {
                    R.string.content_description_collapse
                } else {
                    R.string.content_description_expand
                }
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = description),
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            interactionSource = interactionSource
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            items.forEach {
                DropdownItemView(it) {
                    selectedText = if (isVtC) it.value else it.name
                    selectedLeadingIcon = ColorFinder.textToColor(it.name)
                    expanded = false
                    onOptionSelected(if (isVtC) it.value else it.name)
                }
            }
        }
    }
}

@Composable
private fun DropdownItemView(item: DropdownItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        val color = ColorFinder.textToColor(item.name)
        RoundedSquare(color = color, size = 40.dp)
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = item.name,
                style = textStyleSubhead(),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 2.dp)
            )
            Text(
                text = item.value,
                style = textStyleCaption(),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
        }
    }
}

@AppComponentPreviews
@Composable
private fun CustomDropdownRowPreview() {
    InductanceCalculatorTheme {
        val item1 = DropdownItem(name = "Item 1", value = "Value 1")
        Column {
            DropdownItemView(item1) { }
            DropdownItemView(item1) { }
        }
    }
}

@AppComponentPreviews
@Composable
private fun CustomDropdownPreview() {
    val item1 = DropdownItem(name = "Item 1", value = "Value 1")
    val item2 = DropdownItem(name = "Item 2", value = "Value 2")
    val item3 = DropdownItem(name = "Item 3", value = "Value 3")
    val item4 = DropdownItem(name = "Item 4", value = "Value 4")
    val item5 = DropdownItem(name = "Item 5", value = "Value 5")
    val item6 = DropdownItem(name = "Item 6", value = "Value 6")
    val list = listOf(item1, item2, item3, item4, item5, item6)
    InductanceCalculatorTheme {
        Column {
            ImageTextDropDownMenu(Modifier, R.string.hint_band_1, "", list) { }
            ImageTextDropDownMenu(Modifier, R.string.hint_band_1, "Red", list) { }
        }
    }
}

@AppComponentPreviews
@Composable
private fun TextDropdownRowPreview() {
    InductanceCalculatorTheme {
        val item1 = "unit"
        Column {
            TextDropDownItemView(item1) { }
            TextDropDownItemView(item1) { }
        }
    }
}

@AppComponentPreviews
@Composable
private fun TextDropdownPreview() {
    InductanceCalculatorTheme {
        val list = listOf("item1", "item2", "item3", "item4", "item5", "item6")
        Column {
            AppDropDownMenu(Modifier, R.string.hint_units, "", list) { }
            AppDropDownMenu(Modifier, R.string.hint_units, "Red", list) { }
            AppDropDownMenu(Modifier, R.string.hint_units, "Red", list) { }
        }
    }
}
