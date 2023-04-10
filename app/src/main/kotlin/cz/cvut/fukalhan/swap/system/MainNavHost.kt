package cz.cvut.fukalhan.swap.system

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.system.ProfileScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = SwapAppTheme.colors.background,
        bottomBar = { BottomBar(navController) }
    ) {
        NavHost(navController, MainScreen.Profile.route) {
            composable(MainScreen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = SwapAppTheme.colors.componentBackground,
        contentColor = SwapAppTheme.colors.primary,
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier.height(60.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                },
                label = { Text(stringResource(screen.labelRes)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
