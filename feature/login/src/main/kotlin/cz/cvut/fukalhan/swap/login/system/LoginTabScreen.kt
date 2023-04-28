package cz.cvut.fukalhan.swap.login.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.system.signin.SignInScreen
import cz.cvut.fukalhan.swap.login.system.signup.SignUpScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginTabScreen(
    onScreenInit: (ScreenState) -> Unit,
    navigateToMainScreen: () -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }

    TopBar(onScreenInit, tabIndex) {
        tabIndex = it
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (tabIndex) {
            0 -> SignInScreen(koinViewModel(), navigateToMainScreen)
            1 -> SignUpScreen(koinViewModel(), navigateToMainScreen)
        }
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    tabIndex: Int,
    onTabClick: (Int) -> Unit
) {
    val tabs = listOf(stringResource(R.string.signIn), stringResource(R.string.signUp))

    onScreenInit(
        ScreenState {
            TabRow(
                selectedTabIndex = tabIndex,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                title,
                                style = SwapAppTheme.typography.titleSecondary
                            )
                        },
                        modifier = Modifier.background(SwapAppTheme.colors.primary),
                        selected = tabIndex == index,
                        onClick = { onTabClick(index) },
                    )
                }
            }
        }
    )
}
