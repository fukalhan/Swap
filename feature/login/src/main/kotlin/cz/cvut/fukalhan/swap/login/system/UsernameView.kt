package cz.cvut.fukalhan.swap.login.system

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.login.R

@Composable
fun UsernameView(username: String, onValueChange: (String) -> Unit) {

    Column {
        Text(
            text = stringResource(id = R.string.username),
        )

        TextField(
            value = username,
            singleLine = true,
            onValueChange = onValueChange,
            colors = TextFieldDefaults
                .textFieldColors(
                    cursorColor = SwapAppTheme.colors.primary,
                    focusedIndicatorColor = SwapAppTheme.colors.primary
                )
        )
    }
}