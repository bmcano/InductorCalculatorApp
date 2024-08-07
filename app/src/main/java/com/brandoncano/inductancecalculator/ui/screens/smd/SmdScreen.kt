package com.brandoncano.inductancecalculator.ui.screens.smd

import android.content.Context
import android.graphics.Picture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brandoncano.inductancecalculator.R
import com.brandoncano.inductancecalculator.components.SmdTolerance
import com.brandoncano.inductancecalculator.model.InductorViewModelFactory
import com.brandoncano.inductancecalculator.model.smd.InductorSmd
import com.brandoncano.inductancecalculator.model.smd.InductorSmdViewModel
import com.brandoncano.inductancecalculator.ui.MainActivity
import com.brandoncano.inductancecalculator.ui.composables.AboutAppMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ClearSelectionsMenuItem
import com.brandoncano.inductancecalculator.ui.composables.FeedbackMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ShareImageMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ShareTextMenuItem
import com.brandoncano.inductancecalculator.ui.theme.InductorCalculatorTheme
import com.brandoncano.inductancecalculator.util.formatInductance
import com.brandoncano.inductancecalculator.util.isSmdInputInvalid
import com.brandoncano.sharedcomponents.composables.AppDropDownMenu
import com.brandoncano.sharedcomponents.composables.AppMenuTopAppBar
import com.brandoncano.sharedcomponents.composables.AppScreenPreviews
import com.brandoncano.sharedcomponents.composables.AppTextField
import java.util.Locale

@Composable
fun SmdScreen(
    context: Context,
    navController: NavController,
    viewModel: InductorSmdViewModel,
    smdResistor: LiveData<InductorSmd>
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ContentView(context, navController, viewModel, smdResistor)
    }
}

@Composable
private fun ContentView(
    context: Context,
    navController: NavController,
    viewModel: InductorSmdViewModel,
    smdResistor: LiveData<InductorSmd>
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val showMenu = remember { mutableStateOf(false) }
    var reset by remember { mutableStateOf(false) }
    val inductor by smdResistor.observeAsState(InductorSmd())
    var code by remember { mutableStateOf(inductor.code) }
    var tolerance by remember { mutableStateOf(inductor.tolerance) }
    var isError by remember { mutableStateOf(false) }
    var picture = remember { Picture() }

    fun postSelectionActions() {
        reset = false
        viewModel.updateValues(code, tolerance)
        isError = inductor.isSmdInputInvalid()
        if (!isError) {
            viewModel.saveInductorValues(inductor)
            inductor.formatInductance()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppMenuTopAppBar(stringResource(R.string.title_smd), interactionSource, showMenu) {
            ClearSelectionsMenuItem {
                showMenu.value = false
                viewModel.clear()
                reset = true
                focusManager.clearFocus()
                tolerance = ""
            }
            ShareTextMenuItem(context, inductor.toString(), showMenu)
            ShareImageMenuItem(context, showMenu, picture)
            FeedbackMenuItem(context, showMenu)
            AboutAppMenuItem(navController, showMenu)
        }

        picture = smdInductorPicture(inductor, isError)
        AppTextField(
            label = stringResource(id = R.string.hint_smd_code),
            modifier = Modifier.padding(top = 24.dp, start = 32.dp, end = 32.dp),
            text = code,
            reset = reset,
            isError = isError,
            errorMessage = stringResource(id = R.string.error_invalid_code),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        ) {
            code = it.uppercase(Locale.getDefault())
            postSelectionActions()
        }
        AppDropDownMenu(
            label = stringResource(id = R.string.hint_band_4),
            modifier = Modifier.padding(top = 12.dp, start = 32.dp, end = 32.dp),
            selectedOption = inductor.tolerance,
            items = SmdTolerance.getLetterList(),
            reset = reset,
        ) {
            tolerance = it
            focusManager.clearFocus()
            postSelectionActions()
        }
    }
}

@AppScreenPreviews
@Composable
private fun SmdScreenPreview() {
    val app = MainActivity()
    val viewModel = viewModel<InductorSmdViewModel>(factory = InductorViewModelFactory(app))
    val inductor = MutableLiveData<InductorSmd>()
    InductorCalculatorTheme {
        SmdScreen(app, NavController(app), viewModel, inductor)
    }
}
