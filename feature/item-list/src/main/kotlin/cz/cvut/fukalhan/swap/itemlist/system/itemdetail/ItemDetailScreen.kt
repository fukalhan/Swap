package cz.cvut.fukalhan.swap.itemlist.system.itemdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.R

@Composable
fun ItemDetailScreen(
    onNavigateBack: () -> Unit,
    onScreenInit: (ScreenState) -> Unit
) {
    TopBar(onNavigateBack, onScreenInit)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
    }
}

@Composable
fun TopBar(
    onNavigateBack: () -> Unit,
    onScreenInit: (ScreenState) -> Unit
) {
    onScreenInit(
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
                    text = stringResource(R.string.itemDetail),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun ItemDetail() {
    Column(
        modifier = Modifier
            .padding(SwapAppTheme.dimensions.smallSidePadding)
            .fillMaxSize()
    ) {
    }
}
