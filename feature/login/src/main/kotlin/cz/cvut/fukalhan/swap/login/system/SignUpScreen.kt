package cz.cvut.fukalhan.swap.login.system

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R
import cz.cvut.fukalhan.swap.login.presentation.SignUpViewModel

const val PASSWORD_MIN_LENGTH = 6

@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {
    val scrollState = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    var passwordMatch by remember { mutableStateOf(true) }
    var passwordTooShort by remember { mutableStateOf(true) }
    var fieldsEmpty by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailView(email) {
            email = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        UsernameView(username) {
            username = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        PasswordView(password) {
            password = it
            passwordMatch = password == passwordCheck
            passwordTooShort = password.length < PASSWORD_MIN_LENGTH
        }
        Spacer(modifier = Modifier.height(10.dp))

        PasswordView(passwordCheck) {
            passwordCheck = it
            passwordMatch = password == passwordCheck
        }
        Spacer(modifier = Modifier.height(10.dp))

        PasswordTooShortMessage(passwordTooShort)
        PasswordsDontMatchMessage(passwordMatch)
        FieldsEmptyMessage(fieldsEmpty)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary),
            onClick = {
                fieldsEmpty = email.isEmpty() || username.isEmpty() || password.isEmpty()

                if (passwordMatch && !passwordTooShort && !fieldsEmpty) {
                    viewModel.signUpUser(email, username, password)
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.signUp),
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}

@Composable
fun PasswordTooShortMessage(passwordTooShort: Boolean) {
    if (passwordTooShort) {
        Text(
            text = stringResource(id = R.string.passwordTooShort)
        )
    }
}

@Composable
fun PasswordsDontMatchMessage(passwordMatch: Boolean) {
    if (!passwordMatch) {
        Text(
            text = stringResource(id = R.string.passwordMustBeTheSame),
        )
    }
}

@Composable
fun FieldsEmptyMessage(fieldsEmpty: Boolean) {
    if (fieldsEmpty) {
        Text(
            text = stringResource(id = R.string.fieldsMustBeFilled),
        )
    }
}
