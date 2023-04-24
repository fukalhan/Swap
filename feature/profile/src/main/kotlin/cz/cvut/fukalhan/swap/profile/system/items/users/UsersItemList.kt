package cz.cvut.fukalhan.swap.profile.system.items.users

import androidx.compose.foundation.background
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
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.swap.navigation.presentation.SecondaryScreen
import cz.cvut.fukalhan.swap.profile.presentation.items.Empty
import cz.cvut.fukalhan.swap.profile.presentation.items.Failure
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemListState
import cz.cvut.fukalhan.swap.profile.presentation.items.ItemState
import cz.cvut.fukalhan.swap.profile.presentation.items.Loading
import cz.cvut.fukalhan.swap.profile.presentation.items.Success
import cz.cvut.fukalhan.swap.profile.presentation.items.UserItemsViewModel

@Composable
fun UsersItemList(
    viewModel: UserItemsViewModel,
    navController: NavHostController
) {
    val itemListState: ItemListState by viewModel.itemListState.collectAsState()
    val user = Firebase.auth.currentUser
    val effect = remember {
        {
            user?.let {
                viewModel.getUserItems(it.uid)
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
        ResolveState(itemListState, navController)
    }
}

@Composable
fun ResolveState(
    state: ItemListState,
    navController: NavHostController
) {
    when (state) {
        is Loading -> LoadingView()
        is Success -> UserItemListContent(state.items, navController)
        is Failure -> FailureView(state.message)
        is Empty -> EmptyView(state.message)
        else -> {}
    }
}

@Composable
fun UserItemListContent(
    items: List<ItemState>,
    navController: NavHostController
) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        items(items) { itemState ->
            UsersItemCard(itemState) {
                navController.navigate("${SecondaryScreen.ItemDetail.route}/${itemState.id}")
            }
        }
    }
}