package cz.cvut.fukalhan.swap.profile.system.items.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ItemCard
import cz.cvut.fukalhan.design.system.components.ItemStateView
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemState
import cz.cvut.fukalhan.swap.profile.system.items.common.ItemPicture

const val MAX_LINES = 1

@Composable
fun UsersItemCard(
    itemState: ItemState,
    onItemClick: () -> Unit
) {
    ItemCard(onItemClick) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ItemStateView(
                itemState.state == State.RESERVED || itemState.state == State.SWAPPED,
                itemState.state.label,
                Modifier.align(Alignment.TopCenter)
            )
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
