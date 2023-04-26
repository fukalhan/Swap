package cz.cvut.fukalhan.swap.navigation.presentation

import cz.cvut.fukalhan.swap.navigation.R

// Initial navigation between login screen and main screen of the app
sealed class InitNavScreen(val route: String) {
    object Login : InitNavScreen("login")
    object Main : InitNavScreen("main")
}

// Screens in main part of the app, contain route and icon for the bottom bar navigation
sealed class MainScreen(
    val route: String,
    val iconRes: Int,
) {
    object Items : MainScreen("items", R.drawable.box)
    object Profile : MainScreen("profile", R.drawable.profile)
    object AddItem : MainScreen("addItem", R.drawable.add)
    object Messages : MainScreen("messages", R.drawable.message)
}

val menuItems = listOf(
    MainScreen.Items,
    MainScreen.Profile,
    MainScreen.AddItem,
    MainScreen.Messages
)

// The screens in the app which don't display bottom bar navigation,
// they are navigated to from the main screens
sealed class SecondaryScreen(val route: String) {
    object ItemDetail : SecondaryScreen("itemDetail")
    object Message : SecondaryScreen("message")
    object Settings : SecondaryScreen("settings")
    object ProfileDetail : SecondaryScreen("profileDetail")
    object AddReview : SecondaryScreen("addReview")
}
