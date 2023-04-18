package cz.cvut.fukalhan.swap.login.system.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.signin.SignInViewModel
import cz.cvut.fukalhan.swap.login.system.common.LoadingView
import cz.cvut.fukalhan.swap.login.system.common.LoginButton
import cz.cvut.fukalhan.swap.login.system.common.LoginValidityCheckMessage
import cz.cvut.fukalhan.swap.login.system.common.LoginView
import cz.cvut.fukalhan.swap.login.system.common.OnFailState
import cz.cvut.fukalhan.swap.login.system.common.OnSuccessState
import cz.cvut.fukalhan.swap.login.system.common.PasswordView

@Composable
fun SignInScreen(
    viewModel: SignInViewModel,
    navigateToMainScreen: () -> Unit
) {
    val signInState: LoginState by viewModel.signInState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var fieldsEmpty by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(signInState)
        OnSuccessState(signInState) {
            viewModel.setStateToInit()
            navigateToMainScreen()
        }
        OnFailState(signInState)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginView(email, R.string.email) {
                email = it
            }

            PasswordView(password) {
                password = it
            }

            LoginValidityCheckMessage(!fieldsEmpty, R.string.fieldsMustBeFilled)

            LoginButton(R.string.signIn) {
                fieldsEmpty = email.isEmpty() || password.isEmpty()

                if (!fieldsEmpty) {
                    viewModel.signInUser(email.trim(), password.trim())
                }
            }
        }
    }
}
