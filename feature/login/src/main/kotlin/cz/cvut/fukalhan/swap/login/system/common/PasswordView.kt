package cz.cvut.fukalhan.swap.login.system.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R

@Composable
fun PasswordView(password: String, onValueChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        Text(
            text = stringResource(id = R.string.password)
        )

        TextField(
            value = password,
            singleLine = true,
            colors = TextFieldDefaults
                .textFieldColors(
                    cursorColor = SwapAppTheme.colors.primary,
                    focusedIndicatorColor = SwapAppTheme.colors.primary
                ),
            onValueChange = onValueChange,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) {
                    painterResource(id = R.drawable.visibility_on)
                } else {
                    painterResource(id = R.drawable.visibility_off)
                }
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = image, contentDescription = description)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}
