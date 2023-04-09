package cz.cvut.fukalhan.swap.system

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.cvut.fukalhan.swap.login.system.LoginScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginNavHost() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(navController, Screen.Login.route) {
            composable(Screen.Login.route) {
                LoginScreen(koinViewModel(), koinViewModel())
            }
        }
    }
}
