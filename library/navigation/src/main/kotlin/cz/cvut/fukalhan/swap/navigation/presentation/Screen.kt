package cz.cvut.fukalhan.swap.navigation.presentation

import cz.cvut.fukalhan.swap.navigation.R

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
}

sealed class MainScreen(
    val route: String,
    val iconRes: Int,
) {
    object Items : MainScreen("items", R.drawable.blender)
    object Profile : MainScreen("profile", R.drawable.profile)
    object Settings : MainScreen("settings", R.drawable.settings)
    object AddItem : MainScreen("addItem", R.drawable.add)
}

val items = listOf(
    MainScreen.Items,
    MainScreen.Profile,
    MainScreen.AddItem
)
