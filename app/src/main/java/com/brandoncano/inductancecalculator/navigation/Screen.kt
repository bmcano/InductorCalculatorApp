package com.brandoncano.inductancecalculator.navigation

/**
 * Note: Keep screens in alphabetical order
 */
sealed class Screen(val route: String) {
    data object About : Screen("about_screen")
    data object ColorToValue: Screen("color_to_value_screen")
    data object Home : Screen("home_screen")
    // data object InductorDesign : Screen("inductor_design_screen") // TODO
    data object Smd: Screen("smd_screen")
    data object ValueToColor: Screen("value_to_color_screen")
}
