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

    data object Login : SecondaryScreen("login")

    data object ItemDetail : SecondaryScreen("itemDetail")

    data object SearchScreen : SecondaryScreen("searchScreen")

    data object Message : SecondaryScreen("message")

    data object Settings : SecondaryScreen("settings")

    data object ProfileDetail : SecondaryScreen("profileDetail")

    data object AddReview : SecondaryScreen("addReview")

    data object Notifications : SecondaryScreen("notifications")

    data object AddEvent : SecondaryScreen("addEvent")

    data object EventDetail : SecondaryScreen("eventDetail")
}
