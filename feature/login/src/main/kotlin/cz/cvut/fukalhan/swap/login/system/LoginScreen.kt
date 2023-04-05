package cz.cvut.fukalhan.swap.login.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.SignUpViewModel

@Composable
fun LoginScreen(signUpViewModel: SignUpViewModel) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(stringResource(R.string.signIn), stringResource(R.string.signUp))

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title)},
                    modifier = Modifier.background(SwapAppTheme.colors.secondary),
                    selected = tabIndex == index,
                    onClick = { tabIndex = index}
                )
            }
        }

        when(tabIndex) {
            0 -> SignInScreen()
            1 -> SignUpScreen(signUpViewModel)
        }
    }
}