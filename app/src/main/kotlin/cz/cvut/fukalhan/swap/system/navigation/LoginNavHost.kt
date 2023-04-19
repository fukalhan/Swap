package cz.cvut.fukalhan.swap.system.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fukalhan.swap.login.system.LoginScreen
import cz.cvut.fukalhan.swap.navigation.presentation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginNavHost() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController, Screen.Main.route) {
            composable(Screen.Login.route) {
                LoginScreen(koinViewModel(), koinViewModel(), navController)
            }

            composable(Screen.Main.route) {
                MainNavHost {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route)
                    }
                }
            }
        }
    }
}
