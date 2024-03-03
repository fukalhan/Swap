package cz.cvut.fukalhan.swap.itemdetail.system

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.theme.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.FailureView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.theme.semiTransparentBlack
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.itemdetail.presentation.CreateChannelFailure
import cz.cvut.fukalhan.swap.itemdetail.presentation.CreateChannelSuccess
import cz.cvut.fukalhan.swap.itemdetail.presentation.Failure
import cz.cvut.fukalhan.swap.itemdetail.presentation.ItemDetailState
import cz.cvut.fukalhan.swap.itemdetail.presentation.ItemDetailViewModel
import cz.cvut.fukalhan.swap.itemdetail.presentation.Loading
import cz.cvut.fukalhan.swap.itemdetail.presentation.Success

@Composable
fun ItemDetailScreen(
    itemId: String,
    viewModel: ItemDetailViewModel,
    onNavigateBack: () -> Unit,
    onScreenInit: (ScreenState) -> Unit,
    navigateToOwnerProfileDetail: (String) -> Unit,
    navigateToChat: (String) -> Unit
) {
    val itemDetailState: ItemDetailState by viewModel.itemDetailState.collectAsState()

    val user = Firebase.auth.currentUser
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
        ResolveState(
            itemDetailState,
            viewModel,
            setStateToInit = { viewModel.setStateToInit() },
            navigateToOwnerProfileDetail = navigateToOwnerProfileDetail,
            navigateToChat
        )
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
                        tint = SwapAppTheme.colors.onPrimary,
                        modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                    )
                }
                Text(
                    text = stringResource(R.string.itemDetail),
                    style = SwapAppTheme.typography.screenTitle,
                    modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
                )
            }
        }
    )
}

@Composable
fun ResolveState(
    state: ItemDetailState,
    viewModel: ItemDetailViewModel,
    setStateToInit: () -> Unit,
    navigateToOwnerProfileDetail: (String) -> Unit,
    navigateToChat: (String) -> Unit
) {
    when (state) {
        is Loading -> LoadingView(semiTransparentBlack)
        is Success -> ItemDetail(state, viewModel, navigateToOwnerProfileDetail)
        is Failure -> FailureView(state.message)
        is CreateChannelSuccess -> {
            setStateToInit()
            navigateToChat(state.channelId)
        }
        is CreateChannelFailure -> {
            setStateToInit()
            val context = LocalContext.current
            Toast.makeText(context, stringResource(state.message), Toast.LENGTH_SHORT).show()
        }
        else -> {}
    }
}
