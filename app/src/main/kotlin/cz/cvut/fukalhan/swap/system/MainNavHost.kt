package cz.cvut.fukalhan.swap.system

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
    }
}
