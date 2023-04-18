package cz.cvut.fukalhan.swap.profile.system

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R

@Composable
fun SettingsScreen(
    onComposing: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            ScreenState {
                Row {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = null,
                            tint = SwapAppTheme.colors.buttonText,
                            modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                        )
                    }
                    Text(
                        text = stringResource(R.string.profile),
                        style = SwapAppTheme.typography.screenTitle,
                        color = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                    )
                }
            }
        )
    }

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
