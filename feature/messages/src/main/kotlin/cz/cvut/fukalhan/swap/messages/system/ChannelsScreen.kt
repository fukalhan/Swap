package cz.cvut.fukalhan.swap.messages.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.CustomChatTheme
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.swap.messages.R
import cz.cvut.fukalhan.swap.messages.presentation.ChatViewModelFactory
import cz.cvut.fukalhan.swap.navigation.presentation.SecondaryScreen
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.state.channels.list.ChannelsState
import io.getstream.chat.android.compose.ui.channels.list.ChannelList
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel

@Composable
fun ChannelsScreen(
    viewModelFactory: ChatViewModelFactory,
    navController: NavHostController,
    onScreenInit: (ScreenState) -> Unit
) {
    val listViewModel: ChannelListViewModel = viewModel(
        checkNotNull(LocalViewModelStoreOwner.current),
        null,
        viewModelFactory.createChannelsFactory()
    )

    TopBar(onScreenInit)
    CustomChatTheme {
        Channels(listViewModel, navController)
    }
}

@Composable
fun TopBar(
    onScreenInit: (ScreenState) -> Unit,
) {
    onScreenInit(
        ScreenState {
            Text(
                text = stringResource(R.string.messages),
                style = SwapAppTheme.typography.screenTitle,
                color = SwapAppTheme.colors.buttonText,
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )
        }
    )
}

@Composable
fun Channels(
    listViewModel: ChannelListViewModel,
    navController: NavHostController
) {
    val user by listViewModel.user.collectAsState()
    val channelsState = listViewModel.channelsState
    Box(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(bottom = SwapAppTheme.dimensions.bottomScreenPadding)
            .fillMaxSize(),
    ) {
        ChannelsList(channelsState, user, navController)
    }
}

@Composable
fun ChannelsList(
    channelsState: ChannelsState,
    user: User?,
    navController: NavHostController
) {
    ChannelList(
        channelsState = channelsState,
        currentUser = user,
        loadingContent = { LoadingView() },
        emptyContent = { EmptyView() },
        onChannelClick = { channel ->
            navController.navigate("${SecondaryScreen.Message.route}/${channel.id}")
        }
    )
}

@Composable
fun LoadingView() {
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

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.emptyChannels),
            style = SwapAppTheme.typography.titleSecondary,
            color = SwapAppTheme.colors.textSecondary
        )
    }
}