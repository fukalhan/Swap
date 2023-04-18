package cz.cvut.fukalhan.swap.system.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme

@Composable
fun TopBar(
    screenState: ScreenState
) {
    TopAppBar(
        backgroundColor = SwapAppTheme.colors.primary,
        contentColor = SwapAppTheme.colors.buttonText,
        elevation = SwapAppTheme.dimensions.elevation,
        modifier = Modifier.height(SwapAppTheme.dimensions.bar),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            screenState.topBarContent?.invoke(this)
        }
    }
}
