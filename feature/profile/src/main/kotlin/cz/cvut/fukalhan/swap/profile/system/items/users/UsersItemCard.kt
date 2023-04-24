package cz.cvut.fukalhan.swap.profile.system.items.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemState
import cz.cvut.fukalhan.swap.profile.system.items.common.ItemPicture
import cz.cvut.fukalhan.swap.profile.system.items.common.StateView

const val MAX_LINES = 1

@Composable
fun UsersItemCard(itemState: ItemState) {
    Surface(
        elevation = SwapAppTheme.dimensions.elevation,
        color = SwapAppTheme.colors.backgroundSecondary,
        modifier = Modifier.padding(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                StateView(itemState.state, Modifier.align(Alignment.TopCenter))
                ItemPicture(itemState.imageUri)
            }

            Text(
                modifier = Modifier
                    .padding(SwapAppTheme.dimensions.sidePadding)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                text = itemState.name,
                style = SwapAppTheme.typography.titleSecondary,
                maxLines = MAX_LINES,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
