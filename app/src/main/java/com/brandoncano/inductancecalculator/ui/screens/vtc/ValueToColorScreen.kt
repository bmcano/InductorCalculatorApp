package com.brandoncano.inductancecalculator.ui.screens.vtc

import android.content.Context
import android.graphics.Picture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.components.DropdownLists
import com.brandoncano.inductancecalculator.model.InductorViewModelFactory
import com.brandoncano.inductancecalculator.model.vtc.InductorVtc
import com.brandoncano.inductancecalculator.model.vtc.InductorVtcViewModel
import com.brandoncano.inductancecalculator.ui.MainActivity
import com.brandoncano.inductancecalculator.ui.composables.AboutAppMenuItem
import com.brandoncano.inductancecalculator.ui.composables.AppDivider
import com.brandoncano.inductancecalculator.ui.composables.AppDropDownMenu
import com.brandoncano.inductancecalculator.ui.composables.AppMenuTopAppBar
import com.brandoncano.inductancecalculator.ui.composables.AppScreenPreviews
import com.brandoncano.inductancecalculator.ui.composables.AppTextField
import com.brandoncano.inductancecalculator.ui.composables.ClearSelectionsMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ColorToValueMenuItem
import com.brandoncano.inductancecalculator.ui.composables.FeedbackMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ImageTextDropDownMenu
import com.brandoncano.inductancecalculator.ui.composables.ShareImageMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ShareTextMenuItem
import com.brandoncano.inductancecalculator.ui.screens.ctv.FiveBandInductorInfo
import com.brandoncano.inductancecalculator.ui.theme.InductorCalculatorTheme
import com.brandoncano.inductancecalculator.util.formatInductor
import com.brandoncano.inductancecalculator.util.isInvalidInput
import com.brandoncano.inductancecalculator.util.shareableText

@Composable
fun ValueToColorScreen(
    context: Context,
    navController: NavController,
    viewModel: InductorVtcViewModel,
    inductorVtc: LiveData<InductorVtc>
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ContentView(context, navController, viewModel, inductorVtc)
    }
}

@Composable
private fun ContentView(
    context: Context,
    navController: NavController,
    viewModel: InductorVtcViewModel,
    inductorVtc: LiveData<InductorVtc>
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val showMenu = remember { mutableStateOf(false) }
    var reset by remember { mutableStateOf(false) }
    val inductor by inductorVtc.observeAsState(InductorVtc())
    var inductance by remember { mutableStateOf(inductor.inductance) }
    var units by remember { mutableStateOf(inductor.units) }
    var tolerance by remember { mutableStateOf(inductor.tolerance) }
    var isError by remember { mutableStateOf(false) }
    var picture = remember { Picture() }

    fun postSelectionActions() {
        reset = false
        viewModel.updateValues(inductance, units, tolerance)
        isError = inductor.isInvalidInput()
        if (!isError) {
            viewModel.saveInductorValues(inductor)
            inductor.formatInductor()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isError) {
            inductor.formatInductor()
        }
        AppMenuTopAppBar(
            titleText = stringResource(R.string.title_value_to_color),
            interactionSource = interactionSource,
            showMenu = showMenu,
        ) {
            ColorToValueMenuItem(navController, showMenu)
            ClearSelectionsMenuItem {
                showMenu.value = false
                reset = true
                isError = false
                viewModel.clear()
                focusManager.clearFocus()
                tolerance = ""
            }
            ShareTextMenuItem(context, inductor.shareableText(), showMenu)
            ShareImageMenuItem(context, showMenu, picture)
            FeedbackMenuItem(context, showMenu)
            AboutAppMenuItem(navController, showMenu)
        }

        picture = inductorPicture(inductor, isError)
        AppTextField(
            modifier = Modifier.padding(top = 24.dp),
            label = R.string.hint_inductance,
            text = inductance,
            reset = reset,
            isError = isError,
            errorMessage = stringResource(id = R.string.error_invalid_inductance)
        ) {
            inductance = it
            postSelectionActions()
        }
        AppDropDownMenu(
            modifier = Modifier.padding(top = 12.dp),
            label = R.string.hint_units,
            selectedOption = units,
            items = DropdownLists.UNITS_LIST,
            reset = reset,
        ) {
            units = it
            focusManager.clearFocus()
            postSelectionActions()
        }
        ImageTextDropDownMenu(
            modifier = Modifier.padding(top = 12.dp),
            label = R.string.hint_band_4,
            selectedOption = tolerance,
            items = DropdownLists.TOLERANCE_LIST,
            reset = reset,
            isVtC = true
        ) {
            tolerance = it
            focusManager.clearFocus()
            postSelectionActions()
        }
        AppDivider(modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 24.dp))
        FiveBandInductorInfo()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@AppScreenPreviews
@Composable
private fun ValueToColorScreenPreview() {
    val app = MainActivity()
    val viewModel = viewModel<InductorVtcViewModel>(factory = InductorViewModelFactory(app))
    val inductor = MutableLiveData<InductorVtc>()
    InductorCalculatorTheme {
        ValueToColorScreen(app, NavController(app), viewModel, inductor)
    }
}
