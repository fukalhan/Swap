package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R

@Composable
fun SettingsScreen() {
    Column() {
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary)
        ) {
            Text(
                text = stringResource(R.string.signOut),
                style = SwapAppTheme.typography.button,
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}
