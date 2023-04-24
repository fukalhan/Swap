package cz.cvut.fukalhan.swap.profile.system.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R

@Composable
fun SettingsScreen(
    onScreenInit: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit,
    signOut: () -> Unit
) {
    TopBar(onScreenInit, onNavigateBack)
    Settings(signOut)
}

@Composable
fun TopBar(
    onInitScreen: (ScreenState) -> Unit,
    onNavigateBack: () -> Unit,
) {
    onInitScreen(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.settings),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun Settings(
    signOut: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(bottom = SwapAppTheme.dimensions.bottomScreenPadding)
            .fillMaxSize()
            .padding(SwapAppTheme.dimensions.sidePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SignOutButton(signOut)
    }
}

@Composable
fun SignOutButton(
    signOut: () -> Unit
) {
    Button(
        onClick = {
            val auth = Firebase.auth
            auth.signOut()
            signOut()
        },
        colors = ButtonDefaults.buttonColors(SwapAppTheme.colors.buttonPrimary)
    ) {
        Text(
            text = stringResource(R.string.signOut),
            style = SwapAppTheme.typography.button,
            color = SwapAppTheme.colors.buttonText
        )
    }
}
