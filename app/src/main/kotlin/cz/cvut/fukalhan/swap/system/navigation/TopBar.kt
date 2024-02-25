package cz.cvut.fukalhan.swap.system.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.theme.SwapAppTheme

@Composable
fun TopBar(
    screenState: ScreenState
) {
    screenState.topBarContent?.let { topBarContent ->
        TopAppBar(
            elevation = SwapAppTheme.dimensions.elevation,
            modifier = Modifier.height(SwapAppTheme.dimensions.topBar),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                topBarContent.invoke(this)
            }
        }
    }
}
