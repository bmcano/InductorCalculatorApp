package com.brandoncano.inductancecalculator.ui.screens.ctv

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
import com.brandoncano.inductancecalculator.model.ctv.InductorCtv
import com.brandoncano.inductancecalculator.model.ctv.InductorCtvViewModel
import com.brandoncano.inductancecalculator.ui.MainActivity
import com.brandoncano.inductancecalculator.ui.composables.AboutAppMenuItem
import com.brandoncano.inductancecalculator.ui.composables.AppMenuTopAppBar
import com.brandoncano.inductancecalculator.ui.composables.AppScreenPreviews
import com.brandoncano.inductancecalculator.ui.composables.ClearSelectionsMenuItem
import com.brandoncano.inductancecalculator.ui.composables.FeedbackMenuItem
import com.brandoncano.inductancecalculator.ui.composables.ImageTextDropDownMenu
import com.brandoncano.inductancecalculator.ui.theme.InductanceCalculatorTheme

@Composable
fun ColorToValueScreen(
    context: Context,
    navController: NavController,
    viewModel: InductorCtvViewModel,
    inductorCtv: LiveData<InductorCtv>,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        ContentView(context, navController, viewModel, inductorCtv)
    }
}

@Composable
private fun ContentView(
    context: Context,
    navController: NavController,
    viewModel: InductorCtvViewModel,
    inductorCtv: LiveData<InductorCtv>,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val showMenu = remember { mutableStateOf(false) }
    var reset by remember { mutableStateOf(false) }
    val inductor by inductorCtv.observeAsState(InductorCtv())
    var band1 by remember { mutableStateOf(inductor.band1) }
    var band2 by remember { mutableStateOf(inductor.band2) }
    var band3 by remember { mutableStateOf(inductor.band3) }
    var band4 by remember { mutableStateOf(inductor.band4) }
    var picture = remember { Picture() }

    fun postSelectionActions() {
        reset = false
        focusManager.clearFocus()
        viewModel.updateBands(band1, band2, band3, band4)
        viewModel.saveInductorColors(inductor)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppMenuTopAppBar(
            titleText = stringResource(R.string.title_color_to_value),
            interactionSource = interactionSource,
            showMenu = showMenu,
        ) {
//            ValueToColorMenuItem(navController, showMenu)
            ClearSelectionsMenuItem {
                showMenu.value = false
                reset = true
                viewModel.clear()
                focusManager.clearFocus()
            }
//            ShareTextMenuItem(context, inductor.shareableText(), showMenu)
//            ShareImageMenuItem(context, showMenu, picture)
            FeedbackMenuItem(context, showMenu)
            AboutAppMenuItem(navController, showMenu)
        }

        picture = inductorPicture(inductor)
        ImageTextDropDownMenu(
            modifier = Modifier.padding(top = 24.dp),
            label = R.string.hint_band_1,
            selectedOption = band1,
            items = DropdownLists.NUMBER_LIST_NO_BLACK,
            reset = reset,
        ) {
            band1 = it
            postSelectionActions()
        }
        ImageTextDropDownMenu(
            modifier = Modifier.padding(top = 12.dp),
            label = R.string.hint_band_2,
            selectedOption = band2,
            items = DropdownLists.NUMBER_LIST,
            reset = reset,
        ) {
            band2 = it
            postSelectionActions()
        }
        ImageTextDropDownMenu(
            modifier = Modifier.padding(top = 12.dp),
            label = R.string.hint_band_3,
            selectedOption = band3,
            items = DropdownLists.MULTIPLIER_LIST,
            reset = reset,
        ) {
            band3 = it
            postSelectionActions()
        }
        ImageTextDropDownMenu(
            modifier = Modifier.padding(top = 12.dp),
            label = R.string.hint_band_4,
            selectedOption = band4,
            items = DropdownLists.TOLERANCE_LIST,
            reset = reset,
        ) {
            band4 = it
            postSelectionActions()
        }
        // TODO add 5 band info - Double width silver band military standard (proceeds band1)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@AppScreenPreviews
@Composable
private fun ColorToValueScreenPreview() {
    val app = MainActivity()
    val viewModel = viewModel<InductorCtvViewModel>(factory = InductorViewModelFactory(app))
    val inductor = MutableLiveData<InductorCtv>()
    InductanceCalculatorTheme {
        ColorToValueScreen(app, NavController(app), viewModel, inductor)
    }
}
