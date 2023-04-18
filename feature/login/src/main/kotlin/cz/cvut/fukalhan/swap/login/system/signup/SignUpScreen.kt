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
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.signup.SignUpViewModel
import cz.cvut.fukalhan.swap.login.system.common.LoadingView
import cz.cvut.fukalhan.swap.login.system.common.LoginButton
import cz.cvut.fukalhan.swap.login.system.common.LoginValidityCheckMessage
import cz.cvut.fukalhan.swap.login.system.common.LoginView
import cz.cvut.fukalhan.swap.login.system.common.PasswordView
import cz.cvut.fukalhan.swap.login.system.common.ResolveSignInState

const val PASSWORD_MIN_LENGTH = 6

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navigateToMainScreen: () -> Unit
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

    ResolveSignInState(
        signUpState,
        setStateToInit = { viewModel.setStateToInit() }
    ) {
        navigateToMainScreen()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(signUpState)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginView(email, R.string.email) {
                email = it
            }

            LoginView(username, R.string.username) {
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
