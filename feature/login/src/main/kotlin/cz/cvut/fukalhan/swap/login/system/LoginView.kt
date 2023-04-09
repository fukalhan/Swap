package cz.cvut.fukalhan.swap.login.system

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun LoginView(loginField: String, loginFieldLabel: Int, onValueChange: (String) -> Unit) {
    Column {
        Text(
            text = stringResource(loginFieldLabel),
        )

        TextField(
            value = loginField,
            singleLine = true,
            onValueChange = onValueChange,
            colors = TextFieldDefaults
                .textFieldColors(
                    cursorColor = SwapAppTheme.colors.primary,
                    focusedIndicatorColor = SwapAppTheme.colors.primary
                )
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}
