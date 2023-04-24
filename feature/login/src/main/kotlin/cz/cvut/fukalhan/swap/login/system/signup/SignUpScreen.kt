package cz.cvut.fukalhan.swap.login.system.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import cz.cvut.fukalhan.design.system.components.screenstate.FailSnackMessage
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.Failure
import cz.cvut.fukalhan.swap.login.presentation.common.Loading
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.common.Success
import cz.cvut.fukalhan.swap.login.presentation.signup.SignUpViewModel
import cz.cvut.fukalhan.swap.login.system.common.LoginButton
import cz.cvut.fukalhan.swap.login.system.common.LoginInputView
import cz.cvut.fukalhan.swap.login.system.common.LoginValidityCheckMessage
import cz.cvut.fukalhan.swap.login.system.common.PasswordView
import cz.cvut.fukalhan.swap.navigation.presentation.InitNavScreen

const val PASSWORD_MIN_LENGTH = 6

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    val signUpState: LoginState by viewModel.signUpState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordCheck by remember { mutableStateOf("") }
    var passwordMatch by remember { mutableStateOf(true) }
    var passwordValid by remember { mutableStateOf(true) }
    var fieldsEmpty by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(signUpState) {
            viewModel.setStateToInit()
            navController.navigate(InitNavScreen.Main.route)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginInputView(email, R.string.email) {
                email = it
            }

            LoginInputView(username, R.string.username) {
                username = it
            }

            PasswordView(password) {
                password = it
                passwordMatch = password == passwordCheck
                passwordValid = password.length >= PASSWORD_MIN_LENGTH
            }

            PasswordView(passwordCheck) {
                passwordCheck = it
                passwordMatch = password == passwordCheck
            }

            LoginValidityCheckMessage(passwordValid, R.string.passwordTooShort)
            LoginValidityCheckMessage(passwordMatch, R.string.passwordMustBeTheSame)
            LoginValidityCheckMessage(!fieldsEmpty, R.string.fieldsMustBeFilled)

            LoginButton(R.string.signUp) {
                fieldsEmpty = username.isEmpty() || email.isEmpty() || password.isEmpty()

                if (passwordMatch && passwordValid && !fieldsEmpty) {
                    viewModel.signUpUser(email.trim(), username.trim(), password.trim())
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
