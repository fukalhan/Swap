package cz.cvut.fukalhan.swap.system

sealed class Screen(val route: String) {
    object Login : Screen("login")
}
