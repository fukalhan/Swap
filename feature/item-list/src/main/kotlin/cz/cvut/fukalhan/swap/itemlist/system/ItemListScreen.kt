package cz.cvut.fukalhan.swap.itemlist.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.Empty
import cz.cvut.fukalhan.swap.itemlist.presentation.Failure
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListState
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListViewModel
import cz.cvut.fukalhan.swap.itemlist.presentation.ItemState
import cz.cvut.fukalhan.swap.itemlist.presentation.Loading
import cz.cvut.fukalhan.swap.itemlist.presentation.Success
import cz.cvut.fukalhan.swap.itemlist.system.search.SearchScreen

@Composable
fun ItemListScreen(
    viewModel: ItemListViewModel,
    onScreenInit: (ScreenState) -> Unit,
    navigateToItemDetail: (String) -> Unit
) {
    val itemListState: ItemListState by viewModel.itemListState.collectAsState()
    var searchBarVisible by remember { mutableStateOf(false) }

    val effect = remember {
        {
            Firebase.auth.currentUser?.let {
                viewModel.getItems(it.uid)
            }
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    TopBar(onScreenInit, searchBarVisible) {
        searchBarVisible = !searchBarVisible
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SearchScreen(searchBarVisible, { searchBarVisible = !searchBarVisible }) { searchQuery ->
            Firebase.auth.currentUser?.let { user ->
                viewModel.searchItems(user.uid, searchQuery)
                searchBarVisible = !searchBarVisible
            }
        }
        ResolveState(itemListState, viewModel, navigateToItemDetail, !searchBarVisible)
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
    searchBarVisible: Boolean,
    onSearchClick: () -> Unit
) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.items),
                style = SwapAppTheme.typography.screenTitle,
                color = SwapAppTheme.colors.buttonText,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )

            Row {
                if (!searchBarVisible) {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = null,
                            tint = SwapAppTheme.colors.buttonText,
                            modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ResolveState(
    state: ItemListState,
    viewModel: ItemListViewModel,
    navigateToItemDetail: (String) -> Unit,
    itemClickEnabled: Boolean
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> ItemList(state.items, viewModel, navigateToItemDetail, itemClickEnabled)
        is Failure -> FailureView(state.message)
        is Empty -> EmptyView(state.message)
        else -> {}
    }
}

@Composable
fun ItemList(
    items: List<ItemState>,
    viewModel: ItemListViewModel,
    navigateToItemDetail: (String) -> Unit,
    itemClickEnabled: Boolean,
) {
    val user = Firebase.auth.currentUser

    LazyVerticalGrid(
        modifier = Modifier
            .zIndex(0f)
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(
                start = SwapAppTheme.dimensions.smallSidePadding,
                top = SwapAppTheme.dimensions.smallSidePadding,
                end = SwapAppTheme.dimensions.smallSidePadding,
                bottom = SwapAppTheme.dimensions.bottomScreenPadding
            )
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(SwapAppTheme.dimensions.smallSidePadding),
        horizontalArrangement = Arrangement.spacedBy(SwapAppTheme.dimensions.smallSidePadding)
    ) {
        items(items) { itemState ->
            ItemCard(itemClickEnabled, itemState, navigateToItemDetail) { isLiked ->
                user?.let {
                    viewModel.toggleItemLike(it.uid, itemState.id, isLiked)
                }
            }
        }
    }
}
