package cz.cvut.fukalhan.swap.login.system.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.signup.SignUpState
import cz.cvut.fukalhan.swap.login.presentation.signup.SignUpViewModel
import cz.cvut.fukalhan.swap.login.system.LoginValidityCheckMessage
import cz.cvut.fukalhan.swap.login.system.LoginView
import cz.cvut.fukalhan.swap.login.system.PasswordView

const val PASSWORD_MIN_LENGTH = 6

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val signUpState: SignUpState by viewModel.signUpState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordCheck by remember { mutableStateOf("") }
    var passwordMatch by remember { mutableStateOf(true) }
    var passwordValid by remember { mutableStateOf(true) }
    var fieldsEmpty by remember { mutableStateOf(false) }

    var showOnSignUpFailureMessage by remember { mutableStateOf(false) }

    ResolveSignUpState(
        viewModel,
        signUpState,
        onSuccess = { },
        onFailure = { showOnSignUpFailureMessage = signUpState.result == SignUpState.State.FAILED }
    )

    ShowSignUpStateMessage(context, signUpState.messageResId, showOnSignUpFailureMessage) {
        showOnSignUpFailureMessage = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginView(
            loginField = email,
            loginFieldLabel = R.string.email,
            onValueChange = { email = it }
        )

        LoginView(
            loginField = username,
            loginFieldLabel = R.string.username,
            onValueChange = { username = it }
        )

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

        SignUpButton {
            fieldsEmpty = username.isEmpty() || email.isEmpty() || password.isEmpty()

            if (passwordMatch && passwordValid && !fieldsEmpty) {
                viewModel.signUpUser(email, username, password)
            }
        }
    }
}

@Composable
fun ResolveSignUpState(
    viewModel: SignUpViewModel,
    signUpState: SignUpState,
    onSuccess: () -> Unit,
    onFailure: () -> Unit
) {
    signUpState.resolve(onSuccess, onFailure)
    viewModel.setSignUpStateNeutral()
}

@Composable
fun ShowSignUpStateMessage(context: Context, messageId: Int, showMessage: Boolean, afterShowMessage: () -> Unit) {
    if (showMessage) {
        Toast.makeText(context, stringResource(messageId), Toast.LENGTH_SHORT).show()
        afterShowMessage()
    }
}

@Composable
fun SignUpButton(onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(40.dp))

    Button(
        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary),
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = R.string.signUp),
            color = SwapAppTheme.colors.buttonText
        )
    }
}
