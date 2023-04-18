package cz.cvut.fukalhan.swap.profile.system.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.ScreenState
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

@Composable
fun Settings(
    signOut: () -> Unit
) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        shape = RoundedCornerShape(SwapAppTheme.dimensions.roundCorners),
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier
            .padding(
                top = SwapAppTheme.dimensions.smallSidePadding,
                bottom = SwapAppTheme.dimensions.bottomScreenPadding,
                start = SwapAppTheme.dimensions.smallSidePadding,
                end = SwapAppTheme.dimensions.smallSidePadding,
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SwapAppTheme.dimensions.sidePadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    val user = Firebase.auth
                    user.signOut()
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
    }
}
