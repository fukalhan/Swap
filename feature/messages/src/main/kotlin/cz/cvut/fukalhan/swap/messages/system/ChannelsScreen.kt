package cz.cvut.fukalhan.swap.messages.system

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.CustomChatTheme
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.components.screenstate.EmptyView
import cz.cvut.fukalhan.design.system.components.screenstate.LoadingView
import cz.cvut.fukalhan.design.system.semiTransparentBlack
import cz.cvut.fukalhan.swap.messages.R
import cz.cvut.fukalhan.swap.messages.presentation.ChatViewModelFactory
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.state.channels.list.ChannelsState
import io.getstream.chat.android.compose.ui.channels.list.ChannelList
import io.getstream.chat.android.compose.viewmodel.channels.ChannelListViewModel

@Composable
fun ChannelsScreen(
    viewModelFactory: ChatViewModelFactory,
    onScreenInit: (ScreenState) -> Unit,
    onNavigateToChannel: (String, String) -> Unit,
) {
    val listViewModel: ChannelListViewModel = viewModel(
        checkNotNull(LocalViewModelStoreOwner.current),
        null,
        viewModelFactory.createChannelsFactory()
    )

    TopBar(onScreenInit)
    CustomChatTheme {
        Channels(listViewModel, onNavigateToChannel)
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
                modifier = Modifier.padding(start = SwapAppTheme.dimensions.sidePadding)
            )
        }
    )
}

@Composable
fun Channels(
    listViewModel: ChannelListViewModel,
    onNavigateToChannel: (String, String) -> Unit
) {
    val user by listViewModel.user.collectAsState()
    val channelsState = listViewModel.channelsState
    Box(
        modifier = Modifier
            .padding(bottom = SwapAppTheme.dimensions.bottomScreenPadding)
            .fillMaxSize(),
    ) {
        ChannelsList(channelsState, user, onNavigateToChannel)
    }
}

@Composable
fun ChannelsList(
    channelsState: ChannelsState,
    user: User?,
    onNavigateToChannel: (String, String) -> Unit
) {
    ChannelList(
        channelsState = channelsState,
        currentUser = user,
        loadingContent = { LoadingView(semiTransparentBlack) },
        emptyContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EmptyView(R.string.emptyChannels)
            }
        },
        onChannelClick = { channel ->
            onNavigateToChannel(channel.type, channel.id)
        }
    )
}
