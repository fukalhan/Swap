package cz.cvut.fukalhan.swap.login.system.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun LoginButton(label: Int, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(40.dp))

    Button(
        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary),
        onClick = onClick
    ) {
        Text(
            text = stringResource(label),
            color = SwapAppTheme.colors.buttonText
        )
    }
}
