package cz.cvut.fukalhan.swap.login.system

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R

@Composable
fun SignUpScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    var passwordMatch by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailView(email) {
            email = it
        }
        Spacer(modifier = Modifier.height(10.dp))

        PasswordView(password) {
            password = it
            passwordMatch = password == passwordCheck
        }
        Spacer(modifier = Modifier.height(10.dp))

        PasswordView(passwordCheck) {
            passwordCheck = it
            passwordMatch = password == passwordCheck
        }
        Spacer(modifier = Modifier.height(10.dp))

        PasswordDontMatchMessage(passwordMatch)

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = stringResource(id = R.string.signUp),
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}

@Composable
fun PasswordDontMatchMessage(passwordMatch: Boolean) {
    if(!passwordMatch) {
        Text(
            text = stringResource(id = R.string.passwordMustBeTheSame),
        )
    }
}

