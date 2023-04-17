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
    object Items : MainScreen("items", R.string.itemsForSwap, R.drawable.blender)
    object Profile : MainScreen("profile", R.string.profile, R.drawable.profile)
    object AddItem : MainScreen("addItem", R.string.addItem, R.drawable.add)
}

val items = listOf(
    MainScreen.Items,
    MainScreen.Profile,
    MainScreen.AddItem
)
