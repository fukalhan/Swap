package cz.cvut.fukalhan.swap.profile.system.useritems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.presentation.useritems.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.useritems.UserItemsViewModel

@Composable
fun UserItemList(viewModel: UserItemsViewModel) {
    val itemListState: ItemListState by viewModel.items.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .fillMaxWidth(),
        columns = GridCells.Fixed(2)
    ) {
        items(itemListState.items) {
            ItemCard(it)
        }
    }
}
