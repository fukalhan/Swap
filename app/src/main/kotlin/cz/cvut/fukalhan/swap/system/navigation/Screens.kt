package cz.cvut.fukalhan.swap.system.navigation

import cz.cvut.fukalhan.swap.R

// Screens in main part of the app, contain route and icon for the bottom bar navigation
sealed class MainScreen(
    val route: String,
    val iconRes: Int,
    val label: Int
) {
    object Items : MainScreen("items", R.drawable.box, R.string.swap)

    object Profile : MainScreen("profile", R.drawable.profile, R.string.profile)

    object AddItem : MainScreen("addItem", R.drawable.add, R.string.add)

    object Messages : MainScreen("messages", R.drawable.message, R.string.messages)

    object Events : MainScreen("events", R.drawable.calendar, R.string.events)
}

val menuItems = listOf(
    MainScreen.Items,
    MainScreen.Profile,
    MainScreen.AddItem,
    MainScreen.Messages,
    MainScreen.Events
)

// The screens in the app which don't display bottom bar navigation,
// they are navigated to from the main screens
sealed class SecondaryScreen(val route: String) {

    object Login : SecondaryScreen("login")

    object ItemDetail : SecondaryScreen("itemDetail")

    object Message : SecondaryScreen("message")

    object Settings : SecondaryScreen("settings")

    object ProfileDetail : SecondaryScreen("profileDetail")

    object AddReview : SecondaryScreen("addReview")

    object Notifications : SecondaryScreen("notifications")

    object AddEvent : SecondaryScreen("addEvent")

    object EventDetail : SecondaryScreen("eventDetail")
}
