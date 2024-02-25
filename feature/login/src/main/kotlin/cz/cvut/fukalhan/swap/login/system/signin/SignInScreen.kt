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
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.theme.semiTransparentBlack
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.Failure
import cz.cvut.fukalhan.swap.login.presentation.common.Loading
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.common.Success
import cz.cvut.fukalhan.swap.login.presentation.signin.SignInViewModel
import cz.cvut.fukalhan.swap.login.system.common.LoginButton
import cz.cvut.fukalhan.swap.login.system.common.LoginInputView
import cz.cvut.fukalhan.swap.login.system.common.LoginValidityCheckMessage
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
        ResolveState(signInState) {
            viewModel.setStateToInit()
            navigateToMainScreen()
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginInputView(email, R.string.email) {
                email = it
            }

            PasswordView(R.string.password, password) {
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

@Composable
fun ResolveState(
    state: LoginState,
    onSuccessState: () -> Unit
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> onSuccessState()
        is Failure -> FailSnackMessage(state.message)
        else -> {}
    }
}
