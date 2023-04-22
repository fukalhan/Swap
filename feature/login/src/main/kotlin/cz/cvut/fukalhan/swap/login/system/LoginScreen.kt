package cz.cvut.fukalhan.swap.login.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavHostController
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.system.signin.SignInScreen
import cz.cvut.fukalhan.swap.login.system.signup.SignUpScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(stringResource(R.string.signIn), stringResource(R.string.signUp))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    modifier = Modifier.background(SwapAppTheme.colors.secondary),
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }

        when (tabIndex) {
            0 -> SignInScreen(koinViewModel(), navController)
            1 -> SignUpScreen(koinViewModel(), navController)
        }
    }
}
