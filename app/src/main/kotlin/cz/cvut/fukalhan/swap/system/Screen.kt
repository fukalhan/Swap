package cz.cvut.fukalhan.swap.system

import cz.cvut.fukalhan.swap.R

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Main : Screen("main")
}

sealed class MainScreen(
    val route: String,
    val labelRes: Int,
    val iconRes: Int
) {
    object Profile : MainScreen("profile", R.string.profile, R.drawable.profile)
}

val items = listOf(MainScreen.Profile)
