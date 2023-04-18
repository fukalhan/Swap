package cz.cvut.fukalhan.swap.itemlist.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.Failure
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListState
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListViewModel
import cz.cvut.fukalhan.swap.itemlist.presentation.Loading
import cz.cvut.fukalhan.swap.itemlist.presentation.Success

@Composable
fun ItemListScreen(
    viewModel: ItemListViewModel,
    onScreenInit: (ScreenState) -> Unit,
) {
    val itemListState: ItemListState by viewModel.itemListState.collectAsState()

    TopBar(onScreenInit)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(itemListState)
        OnSuccess(itemListState)
        OnFailure(itemListState)
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit
) {
    onScreenInit(
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

@Composable
fun LoadingView(itemListState: ItemListState) {
    if (itemListState is Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(SwapAppTheme.dimensions.icon),
                color = SwapAppTheme.colors.primary
            )
        }
    }
}

@Composable
fun OnSuccess(itemListState: ItemListState) {
    if (itemListState is Success) {
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
}

@Composable
fun OnFailure(itemListState: ItemListState) {
    if (itemListState is Failure) {
        Text(
            text = stringResource(itemListState.message),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )
    }
}
