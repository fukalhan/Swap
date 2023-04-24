package cz.cvut.fukalhan.swap.system.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fukalhan.swap.login.system.LoginTabScreen
import cz.cvut.fukalhan.swap.navigation.presentation.InitNavScreen

@Composable
fun LoginNavHost() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController, InitNavScreen.Main.route) {
            composable(InitNavScreen.Login.route) {
                BackHandler(true) {
                    // Do nothing on back pressed
                }
                LoginTabScreen(navController)
            }

            composable(InitNavScreen.Main.route) {
                MainNavHost {
                    navController.navigate(InitNavScreen.Login.route) {
                        popUpTo(InitNavScreen.Main.route)
                    }
                }
            }
        }
    }
}
