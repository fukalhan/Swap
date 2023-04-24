package cz.cvut.fukalhan.swap.profile.system.items.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.items.Success
import cz.cvut.fukalhan.swap.profile.presentation.items.UserItemsViewModel
import cz.cvut.fukalhan.swap.profile.system.items.common.FailView
import cz.cvut.fukalhan.swap.profile.system.items.common.LoadingView

@Composable
fun UsersItemList(viewModel: UserItemsViewModel) {
    val itemListState: ItemListState by viewModel.itemListState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SwapAppTheme.colors.backgroundSecondary),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(itemListState)
        UserItemListContent(itemListState)
        FailView(itemListState)
    }
}

@Composable
fun UserItemListContent(itemListState: ItemListState) {
    if (itemListState is Success) {
        if (itemListState.items.isEmpty()) {
            Text(
                text = stringResource(R.string.noItemsToDisplay),
                style = SwapAppTheme.typography.titleSecondary,
                color = SwapAppTheme.colors.textPrimary
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .background(SwapAppTheme.colors.backgroundSecondary)
                    .fillMaxSize(),
                columns = GridCells.Fixed(2),
            ) {
                items(itemListState.items) {
                    UsersItemCard(it, {})
                }
            }
        }
    }
}
