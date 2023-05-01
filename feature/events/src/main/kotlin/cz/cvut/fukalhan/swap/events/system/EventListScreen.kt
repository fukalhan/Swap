package cz.cvut.fukalhan.swap.events.system

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.presentation.EventListViewModel

@Composable
fun EventListScreen(
    viewModel: EventListViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateToAddEvent: () -> Unit
) {
    TopBar(onScreenInit, navigateToAddEvent)

    Box(modifier = Modifier.fillMaxSize()) {
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    addEvent: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.swapEvents),
                style = SwapAppTheme.typography.screenTitle,
                color = SwapAppTheme.colors.buttonText,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )

            IconButton(onClick = addEvent) {
                Icon(
                    painter = painterResource(R.drawable.plus),
                    contentDescription = null,
                    tint = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                )
            }
        }
    )
}
