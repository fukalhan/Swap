package cz.cvut.fukalhan.swap.itemlist.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PreviewViewModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.system.components.BasicHeader
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.model.BasicHeaderVo
import cz.cvut.fukalhan.design.wrappers.ScreenContentWrapper
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.swap.itemlist.model.ItemListScreenData
import cz.cvut.fukalhan.swap.itemlist.model.ItemListScreenEvent

/**
 * Screen to display all items available
 */
@Composable
fun ItemListScreen(
    viewModel: ComposeViewModel<ItemListScreenData, ItemListScreenEvent>,
) {
    val viewState by viewModel.viewState.collectAsState()

    ScreenContentWrapper(
        state = viewState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                BasicHeader(
                    model = BasicHeaderVo(
                        title = StringModel.Resource(id = R.string.items)
                    )
                )
            }
        ) { padding ->

            LazyVerticalGrid(
                modifier = Modifier
                    .padding(padding)
                    .padding(bottom = SwapAppTheme.dimensions.bottomScreenPadding)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2)
            ) {
                items(viewState.data.items) { item ->
                    ItemCard(
                        model = item,
                        sendEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@Preview
@Composable
internal fun ItemListScreenPreview() {
    ItemListScreen(
        viewModel = PreviewViewModel(
            state = UiState(
                data = ItemListScreenData()
            )
        )
    )
}