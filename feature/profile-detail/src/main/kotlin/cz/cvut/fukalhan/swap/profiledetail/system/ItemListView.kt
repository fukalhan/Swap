package cz.cvut.fukalhan.swap.profiledetail.system

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.ItemCard
import cz.cvut.fukalhan.design.system.components.ItemStateView
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.profiledetail.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profiledetail.presentation.items.ItemListViewModel
import cz.cvut.fukalhan.swap.profiledetail.presentation.items.ItemState

const val ITEM_MAX_LINES = 1

@Composable
fun ItemListView(
    userId: String,
    viewModel: ItemListViewModel,
    navigateToItemDetail: (String) -> Unit,
) {
    val itemListState by viewModel.itemListState.collectAsState()
    val effect = remember {
        {
            Firebase.auth.currentUser?.let {
                viewModel.getUserItems(it.uid, userId)
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
            Firebase.auth.currentUser?.let {
                viewModel.toggleItemLike(it.uid, userId, itemId, isLiked)
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
        is ItemListState.Loading -> LoadingView()
        is ItemListState.DataLoaded -> ItemList(
            items = state.items,
            navigateToItemDetail,
            onItemLikeButtonClick
        )
        is ItemListState.Empty -> EmptyView(state.message)
        is ItemListState.Failure -> FailureView(state.message)
        else -> {}
    }
}

@Composable
fun ItemList(
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

@Composable
fun LikedItemCard(
    itemState: ItemState,
    onLikeButtonClick: (String, Boolean) -> Unit,
    onItemClick: () -> Unit,
) {
    ItemCard(onClick = onItemClick) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            ItemStateView(
                itemState.state == State.RESERVED || itemState.state == State.SWAPPED,
                itemState.state.label,
                Modifier.align(Alignment.TopCenter)
            )
            ItemPicture(itemState.imageUri)
            LikeButton(
                itemState,
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(SwapAppTheme.dimensions.smallSidePadding)
                    .size(SwapAppTheme.dimensions.icon)
            ) {
                onLikeButtonClick(itemState.id, it)
            }
        }

        Text(
            modifier = Modifier
                .padding(SwapAppTheme.dimensions.sidePadding)
                .wrapContentHeight()
                .fillMaxWidth(),
            text = itemState.name,
            style = SwapAppTheme.typography.titleSecondary,
            maxLines = ITEM_MAX_LINES,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ItemPicture(uri: Uri) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(uri)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.camera),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun LikeButton(
    state: ItemState,
    modifier: Modifier,
    onLikeButtonClick: (Boolean) -> Unit,
) {
    var isLiked by remember { mutableStateOf(state.isLiked) }

    IconButton(
        onClick = {
            isLiked = !isLiked
            onLikeButtonClick(isLiked)
        },
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(if (isLiked) R.drawable.colored_heart else R.drawable.heart),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
