package cz.cvut.fukalhan.swap.profile.system.items.liked

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.profile.presentation.items.Empty
import cz.cvut.fukalhan.swap.profile.presentation.items.Failure
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemState
import cz.cvut.fukalhan.swap.profile.presentation.items.LikedItemListViewModel
import cz.cvut.fukalhan.swap.profile.presentation.items.Loading
import cz.cvut.fukalhan.swap.profile.presentation.items.Success

@Composable
fun LikedItemList(
    viewModel: LikedItemListViewModel,
    navigateToItemDetail: (String) -> Unit
) {
    val itemListState: ItemListState by viewModel.itemListState.collectAsState()
    val user = Firebase.auth.currentUser
    val effect = remember {
        {
            user?.let {
                viewModel.getLikedItems(it.uid)
            }
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResolveState(itemListState, navigateToItemDetail) { itemId, isLiked ->
            user?.let {
                viewModel.toggleItemLike(it.uid, itemId, isLiked)
            }
        }
    }
}

@Composable
fun ResolveState(
    state: ItemListState,
    navigateToItemDetail: (String) -> Unit,
    onItemLikeButtonClick: (String, Boolean) -> Unit
) {
    when (state) {
        is Loading -> LoadingView()
        is Success -> LikedItemListContent(state.items, navigateToItemDetail, onItemLikeButtonClick)
        is Failure -> FailureView(state.message)
        is Empty -> EmptyView(state.message)
        else -> {}
    }
}

@Composable
fun LikedItemListContent(
    items: List<ItemState>,
    navigateToItemDetail: (String) -> Unit,
    onItemLikeButtonClick: (String, Boolean) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        items(items) { itemState ->
            LikedItemCard(
                itemState,
                onItemLikeButtonClick
            ) {
                navigateToItemDetail(itemState.id)
            }
        }
    }
}
