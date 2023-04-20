package cz.cvut.fukalhan.swap.profile.system.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.items.LikedItemListViewModel
import cz.cvut.fukalhan.swap.profile.presentation.items.Success

@Composable
fun LikedItemList(
    viewModel: LikedItemListViewModel
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
        modifier = Modifier
            .fillMaxSize()
            .background(SwapAppTheme.colors.backgroundSecondary),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(itemListState)
        LikedItemListContent(itemListState, viewModel, user)
        FailView(itemListState)
    }
}

@Composable
fun LikedItemListContent(
    itemListState: ItemListState,
    viewModel: LikedItemListViewModel,
    user: FirebaseUser?
) {
    if (itemListState is Success) {
        LazyVerticalGrid(
            modifier = Modifier
                .background(SwapAppTheme.colors.backgroundSecondary)
                .fillMaxWidth(),
            columns = GridCells.Fixed(2),
        ) {
            items(itemListState.items) { itemState ->
                user?.let { user ->
                    LikeItemCard(itemState) { isLiked ->
                        viewModel.toggleItemLike(user.uid, itemState.id, isLiked)
                    }
                }
            }
        }
    }
}
