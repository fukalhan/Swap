package cz.cvut.fukalhan.swap.itemlist.system.itemdetail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.itemlist.R
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.CreateChannelFailure
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.CreateChannelSuccess
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.Failure
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.ItemDetailState
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.ItemDetailViewModel
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.Loading
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.Success

@Composable
fun ItemDetailScreen(
    itemId: String,
    viewModel: ItemDetailViewModel,
    onNavigateBack: () -> Unit,
    navigateToMessageScreen: (String) -> Unit,
    onScreenInit: (ScreenState) -> Unit,
) {
    val user = Firebase.auth.currentUser
    val itemDetailState: ItemDetailState by viewModel.itemDetailState.collectAsState()
    val effect = remember {
        {
            user?.let {
                viewModel.getItemDetail(it.uid, itemId)
            }
        }
    }
    LaunchedEffect(Unit) {
        effect()
    }

    TopBar(onNavigateBack, onScreenInit)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingView(itemDetailState)
        OnSuccessView(itemDetailState, viewModel)
        OnFailureView(itemDetailState)
        ResolveCreateChannelState(itemDetailState, navigateToMessageScreen)
    }
}

@Composable
fun TopBar(
    onNavigateBack: () -> Unit,
    onScreenInit: (ScreenState) -> Unit
) {
    onScreenInit(
        ScreenState {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint = SwapAppTheme.colors.buttonText,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.itemDetail),
                    style = SwapAppTheme.typography.screenTitle,
                    color = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun OnSuccessView(
    itemDetailState: ItemDetailState,
    viewModel: ItemDetailViewModel
) {
    if (itemDetailState is Success) {
        ItemDetail(itemDetailState, viewModel)
    }
}

@Composable
fun LoadingView(itemDetailState: ItemDetailState) {
    if (itemDetailState is Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(1f),
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
fun OnFailureView(itemDetailState: ItemDetailState) {
    if (itemDetailState is Failure) {
        Text(
            text = stringResource(itemDetailState.message),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textPrimary
        )
    }
}

@Composable
fun ResolveCreateChannelState(
    itemDetailState: ItemDetailState,
    navigateToMessageScreen: (String) -> Unit
) {
    if (itemDetailState is CreateChannelFailure) {
        val context = LocalContext.current
        Toast.makeText(context, stringResource(itemDetailState.message), Toast.LENGTH_SHORT).show()
    }

    if (itemDetailState is CreateChannelSuccess) {
        navigateToMessageScreen(itemDetailState.channelId)
    }
}
