package cz.cvut.fukalhan.swap.profile.system.items.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.itemdata.model.State

@Composable
fun StateView(state: State, modifier: Modifier) {
    if (state == State.RESERVED || state == State.SWAPPED) {
        Row(
            modifier = modifier
                .background(semiTransparentBlack)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(SwapAppTheme.dimensions.smallSidePadding)
                .zIndex(1f)
        ) {
            Text(
                text = stringResource(state.label),
                style = SwapAppTheme.typography.button,
                color = SwapAppTheme.colors.buttonText
            )
        }
    }
}
