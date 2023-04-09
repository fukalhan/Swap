package cz.cvut.fukalhan.swap.login.system.signin

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.common.LoginState
import cz.cvut.fukalhan.swap.login.presentation.signin.SignInViewModel
import cz.cvut.fukalhan.swap.login.system.common.LoginButton
import cz.cvut.fukalhan.swap.login.system.common.LoginValidityCheckMessage
import cz.cvut.fukalhan.swap.login.system.common.LoginView
import cz.cvut.fukalhan.swap.login.system.common.PasswordView
import cz.cvut.fukalhan.swap.login.system.common.ShowLoginStateMessage

@Composable
fun SignInScreen(viewModel: SignInViewModel) {
    val context = LocalContext.current
    val signInState: LoginState by viewModel.signInState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var fieldsEmpty by remember { mutableStateOf(false) }
    var showOnSignInFailureMessage by remember { mutableStateOf(false) }

    ResolveSignInState(viewModel, signInState, onSuccess = { /*TODO*/ }) {
        showOnSignInFailureMessage = true
    }

    ShowLoginStateMessage(context, signInState.messageResId, showOnSignInFailureMessage) {
        showOnSignInFailureMessage = false
    }

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

@Composable
fun ResolveSignInState(
    viewModel: SignInViewModel,
    signInState: LoginState,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    signInState.resolve(onSuccess, onFailure)
    viewModel.setSignInStateNeutral()
}
