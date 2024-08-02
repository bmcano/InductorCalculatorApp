package com.brandoncano.inductancecalculator.navigation

import android.content.Context
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandoncano.inductancecalculator.model.InductorViewModelFactory
import com.brandoncano.inductancecalculator.model.ctv.InductorCtvViewModel
import com.brandoncano.inductancecalculator.ui.screens.HomeScreen
import com.brandoncano.inductancecalculator.ui.screens.ctv.ColorToValueScreen

/**
 * Note: Keep each navigation route in alphabetical order
 */

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.ColorToValue.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { it }) },
        ) {
            val viewModel = viewModel<InductorCtvViewModel>(factory = InductorViewModelFactory(context))
            val inductor = viewModel.getInductorLiveData()
            ColorToValueScreen(context, navController, viewModel, inductor)
        }
        composable(
            route = Screen.Home.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            HomeScreen(context, navController)
        }
    }
}