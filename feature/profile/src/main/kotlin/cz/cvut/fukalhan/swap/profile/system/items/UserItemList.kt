package cz.cvut.fukalhan.swap.profile.system.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.presentation.items.Failure
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.items.Loading
import cz.cvut.fukalhan.swap.profile.presentation.items.Success
import cz.cvut.fukalhan.swap.profile.presentation.items.UserItemsViewModel

@Composable
fun UserItemList(viewModel: UserItemsViewModel) {
    val itemListState: ItemListState by viewModel.itemListState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SwapAppTheme.colors.backgroundSecondary),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(itemListState)
        ItemListContent(itemListState)
        FailView(itemListState)
    }
}

@Composable
fun LoadingView(itemListState: ItemListState) {
    if (itemListState is Loading) {
        Box(modifier = Modifier.wrapContentSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(SwapAppTheme.dimensions.icon),
                color = SwapAppTheme.colors.primary
            )
        }
    }
}

@Composable
fun ItemListContent(itemListState: ItemListState) {
    if (itemListState is Success) {
        LazyVerticalGrid(
            modifier = Modifier
                .background(SwapAppTheme.colors.backgroundSecondary)
                .fillMaxWidth(),
            columns = GridCells.Fixed(2),
        ) {
            items(itemListState.items) {
                ItemCard(it)
            }
        }
    }
}

@Composable
fun FailView(itemListState: ItemListState) {
    if (itemListState is Failure) {
        Text(
            text = stringResource(itemListState.message),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )
    }
}
