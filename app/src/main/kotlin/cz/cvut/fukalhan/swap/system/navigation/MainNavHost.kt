package cz.cvut.fukalhan.swap.system.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.additem.system.AddItemScreen
import cz.cvut.fukalhan.swap.itemlist.system.ItemListScreen
import cz.cvut.fukalhan.swap.itemlist.system.itemdetail.ItemDetailScreen
import cz.cvut.fukalhan.swap.itemlist.system.message.ChatScreen
import cz.cvut.fukalhan.swap.messages.system.MessageScreen
import cz.cvut.fukalhan.swap.navigation.presentation.MainScreen
import cz.cvut.fukalhan.swap.navigation.presentation.SecondaryScreen
import cz.cvut.fukalhan.swap.profile.system.ProfileScreen
import cz.cvut.fukalhan.swap.profile.system.settings.SettingsScreen
import org.koin.androidx.compose.getKoin
import org.koin.androidx.compose.koinViewModel

const val ITEM_ID = "itemId"
const val CHANNEL_ID = "channelId"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainNavHost(
    signOut: () -> Unit
) {
    val navController = rememberNavController()
    var screenState by remember { mutableStateOf(ScreenState()) }
    var bottomBarVisible by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    bottomBarVisible = when (navBackStackEntry?.destination?.route) {
        MainScreen.AddItem.route -> false
        "${SecondaryScreen.ItemDetail.route}/{$ITEM_ID}" -> false
        "${SecondaryScreen.Message.route}/{$CHANNEL_ID}" -> false
        else -> true
    }

    val user = Firebase.auth.currentUser
    if (user == null) {
        signOut()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = SwapAppTheme.colors.background,
        topBar = { TopBar(screenState) },
        bottomBar = { AnimatedBottomBar(navController, bottomBarVisible) }
    ) {
        NavHost(navController, MainScreen.Profile.route) {
            composable(MainScreen.Items.route) {
                ItemListScreen(
                    koinViewModel(),
                    { screenState = it }
                ) { itemId ->
                    navController.navigate("${SecondaryScreen.ItemDetail.route}/$itemId")
                }
            }

            composable(
                "${SecondaryScreen.ItemDetail.route}/{$ITEM_ID}",
                arguments = listOf(navArgument(ITEM_ID) { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(ITEM_ID)?.let { itemId ->
                    ItemDetailScreen(
                        itemId,
                        koinViewModel(),
                        navController,
                        { navController.popBackStack() },
                    ) {
                        screenState = it
                    }
                }
            }

            composable(
                "${SecondaryScreen.Message.route}/{$CHANNEL_ID}",
                arguments = listOf(navArgument(CHANNEL_ID) { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString(CHANNEL_ID)?.let { channelId ->
                    ChatScreen(
                        channelId,
                        getKoin().get()
                    )
                }
            }

            composable(MainScreen.Profile.route) {
                ProfileScreen(
                    { screenState = it }
                ) {
                    navController.navigate(SecondaryScreen.Settings.route)
                }
            }

            composable(SecondaryScreen.Settings.route) {
                SettingsScreen(
                    onScreenInit = { screenState = it },
                    onNavigateBack = { navController.popBackStack() },
                    signOut = signOut
                )
            }

            composable(MainScreen.AddItem.route) {
                AddItemScreen(
                    koinViewModel(),
                    { screenState = it }
                ) {
                    navController.navigate(MainScreen.Profile.route)
                }
            }

            composable(MainScreen.Messages.route) {
                MessageScreen()
            }
        }
    }
}
