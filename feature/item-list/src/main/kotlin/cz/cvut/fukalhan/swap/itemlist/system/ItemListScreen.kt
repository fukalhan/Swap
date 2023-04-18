package cz.cvut.fukalhan.swap.itemlist.system

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListState
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListViewModel

@Composable
fun ItemListScreen(
    viewModel: ItemListViewModel,
    onComposing: (ScreenState) -> Unit,
) {
    val itemListState: ItemListState by viewModel.items.collectAsState()

    LaunchedEffect(key1 = true) {
        onComposing(
            ScreenState {
                Text(
                    text = stringResource(R.string.items),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        )
    }

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
