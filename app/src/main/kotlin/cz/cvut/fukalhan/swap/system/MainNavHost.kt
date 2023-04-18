package cz.cvut.fukalhan.swap.system

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.R
import cz.cvut.fukalhan.swap.additem.system.AddItemScreen
import cz.cvut.fukalhan.swap.itemlist.system.ItemListScreen
import cz.cvut.fukalhan.swap.profile.system.ProfileScreen
import cz.cvut.fukalhan.swap.profile.system.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    var bottomBarVisible by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var showSettingsButton by remember { mutableStateOf(false) }
    var showBackButton by remember { mutableStateOf(false) }

    bottomBarVisible = when (navBackStackEntry?.destination?.route) {
        MainScreen.AddItem.route -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = SwapAppTheme.colors.background,
        topBar = {
            TopBar(
                navController,
                showSettingsButton,
                showBackButton,
                { navController.navigate(MainScreen.Settings.route) }
            ) {
                navController.popBackStack()
            }
        },
        bottomBar = { AnimatedBottomBar(navController, bottomBarVisible) }
    ) {
        NavHost(navController, MainScreen.Profile.route) {
            composable(MainScreen.Items.route) {
                showSettingsButton = false
                showBackButton = false
                ItemListScreen(koinViewModel())
            }

            composable(MainScreen.Profile.route) {
                showSettingsButton = true
                showBackButton = false
                ProfileScreen(koinViewModel(), koinViewModel())
            }

            composable(MainScreen.Settings.route) {
                showSettingsButton = false
                showBackButton = true
                SettingsScreen()
            }

            composable(MainScreen.AddItem.route) {
                AddItemScreen(koinViewModel()) {
                    showSettingsButton = false
                    showBackButton = false
                    navController.navigate(MainScreen.Profile.route)
                }
            }
        }
    }
}

@Composable
fun TopBar(
    navController: NavController,
    showSettings: Boolean,
    showBackButton: Boolean,
    onSettingClick: () -> Unit,
    onNavigateBackClick: () -> Unit
) {
    val currentScreen = navController.currentDestination?.route

    TopAppBar(
        backgroundColor = SwapAppTheme.colors.primary,
        contentColor = SwapAppTheme.colors.buttonText,
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier.height(SwapAppTheme.dimensions.bar),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showBackButton) {
                    IconButton(onClick = onNavigateBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null,
                            tint = SwapAppTheme.colors.buttonText,
                            modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                        )
                    }
                }

                currentScreen?.let {
                    val labelRes = getScreenLabelRes(it)
                    Text(
                        text = stringResource(labelRes),
                        style = SwapAppTheme.typography.screenTitle,
                        color = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                    )
                }
            }

            if (showSettings) {
                IconButton(onClick = onSettingClick) {
                    Icon(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
            }
        }
    }
}
fun getScreenLabelRes(route: String): Int {
    return when (route) {
        "items" -> R.string.itemsForSwap
        "profile" -> R.string.profile
        "settings" -> R.string.settings
        else -> R.string.addItem
    }
}

@Composable
fun AnimatedBottomBar(
    navController: NavController,
    bottomBarVisibility: Boolean
) {
    AnimatedVisibility(
        visible = bottomBarVisibility,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        BottomBar(navController)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = SwapAppTheme.colors.backgroundSecondary,
        contentColor = SwapAppTheme.colors.primary,
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier.height(SwapAppTheme.dimensions.bar)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                },
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
