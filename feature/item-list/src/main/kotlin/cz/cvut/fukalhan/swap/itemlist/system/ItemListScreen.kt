package cz.cvut.fukalhan.swap.itemlist.system

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListState
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListViewModel

@Composable
fun ItemListScreen(viewModel: ItemListViewModel) {
    val itemListState: ItemListState by viewModel.items.collectAsState()

    LazyVerticalGrid(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth(),
        columns = GridCells.Fixed(2)
    ) {
        items(itemListState.items) {
            ItemCard(it)
        }
    }
}
