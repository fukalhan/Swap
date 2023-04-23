package cz.cvut.fukalhan.swap.itemlist.system.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.cvut.fukalhan.design.presentation.CHANNEL_TYPE
import cz.cvut.fukalhan.design.presentation.ScreenState
import cz.cvut.fukalhan.design.system.CustomChatTheme
import cz.cvut.fukalhan.design.system.SwapAppTheme
import cz.cvut.fukalhan.design.system.lightGrey
import cz.cvut.fukalhan.swap.messages.R
import cz.cvut.fukalhan.swap.messages.presentation.ChatViewModelFactory
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.ConnectionState
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.common.state.MessageMode
import io.getstream.chat.android.compose.state.messages.SelectedMessageOptionsState
import io.getstream.chat.android.compose.ui.components.messageoptions.defaultMessageOptionsState
import io.getstream.chat.android.compose.ui.components.selectedmessage.SelectedMessageMenu
import io.getstream.chat.android.compose.ui.messages.attachments.AttachmentsPicker
import io.getstream.chat.android.compose.ui.messages.composer.MessageComposer
import io.getstream.chat.android.compose.ui.messages.header.MessageListHeader
import io.getstream.chat.android.compose.ui.messages.list.MessageList
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel

@Composable
fun ChatScreen(
    arg: String,
    viewModelFactory: ChatViewModelFactory,
    onScreenInit: (ScreenState) -> Unit,
    navigateBack: () -> Unit
) {
    val channelId = "$CHANNEL_TYPE:$arg"
    val listViewModel: MessageListViewModel = viewModel(
        checkNotNull(LocalViewModelStoreOwner.current),
        null,
        viewModelFactory.createFactory(channelId)
    )
    val attachmentsPickerViewModel: AttachmentsPickerViewModel = viewModel(
        checkNotNull(LocalViewModelStoreOwner.current),
        null,
        viewModelFactory.createFactory(channelId)
    )
    val composerViewModel: MessageComposerViewModel = viewModel(
        checkNotNull(LocalViewModelStoreOwner.current),
        null,
        viewModelFactory.createFactory(channelId)
    )
    onScreenInit(ScreenState())

    CustomChatTheme {
        Chat(listViewModel, attachmentsPickerViewModel, composerViewModel, navigateBack)
    }
}

@Composable
fun Chat(
    listViewModel: MessageListViewModel,
    attachmentsPickerViewModel: AttachmentsPickerViewModel,
    composerViewModel: MessageComposerViewModel,
    navigateBack: () -> Unit
) {
    val isShowingAttachments = attachmentsPickerViewModel.isShowingAttachments
    val selectedMessageState = listViewModel.currentMessagesState.selectedMessageState
    val user by listViewModel.user.collectAsState()
    val channel = listViewModel.channel
    val connectionState by listViewModel.connectionState.collectAsState()
    val messageMode = listViewModel.messageMode

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MessageScreenHeader(
                    channel = channel,
                    user = user,
                    connectionState = connectionState,
                    messageMode = messageMode,
                    navigateBack = navigateBack
                )
            },
            bottomBar = { MessageComposerBar(composerViewModel, attachmentsPickerViewModel) }
        ) {
            MessageList(listViewModel, it)
        }

        if (isShowingAttachments) {
            AttachmentsPicker(
                attachmentsPickerViewModel,
                composerViewModel,
                Modifier.align(Alignment.BottomCenter)
            )
        }

        if (selectedMessageState is SelectedMessageOptionsState) {
            SelectedMessageMenu(
                selectedMessageState,
                listViewModel,
                composerViewModel,
                user,
                Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun MessageScreenHeader(
    channel: Channel,
    user: User?,
    connectionState: ConnectionState,
    messageMode: MessageMode,
    navigateBack: () -> Unit
) {
    MessageListHeader(
        channel = channel,
        currentUser = user,
        connectionState = connectionState,
        messageMode = messageMode,
        onBackPressed = navigateBack,
        modifier = Modifier.wrapContentHeight(),
        color = SwapAppTheme.colors.primary,
        leadingContent = {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier.wrapContentHeight()
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                    tint = SwapAppTheme.colors.buttonText,
                    modifier = Modifier.size(SwapAppTheme.dimensions.icon)
                )
            }
        }
    )
}

@Composable
fun MessageList(
    listViewModel: MessageListViewModel,
    padding: PaddingValues,
) {
    MessageList(
        modifier = Modifier
            .background(SwapAppTheme.colors.backgroundSecondary)
            .padding(padding)
            .fillMaxSize(),
        viewModel = listViewModel,
        onThreadClick = { message ->
            // composerViewModel.setMessageMode(Thread(message))
            listViewModel.openMessageThread(message)
        }
    )
}

@Composable
fun MessageComposerBar(
    composerViewModel: MessageComposerViewModel,
    attachmentsPickerViewModel: AttachmentsPickerViewModel
) {
    MessageComposer(
        viewModel = composerViewModel,
        onAttachmentsClick = {
            attachmentsPickerViewModel.changeAttachmentState(true)
        },
        label = {
            Text(
                text = stringResource(R.string.sendMessage),
                style = ChatTheme.typography.body,
                color = lightGrey
            )
        }
    )
}

@Composable
fun AttachmentsPicker(
    attachmentsPickerViewModel: AttachmentsPickerViewModel,
    composerViewModel: MessageComposerViewModel,
    modifier: Modifier
) {
    AttachmentsPicker(
        attachmentsPickerViewModel = attachmentsPickerViewModel,
        modifier = modifier.height(350.dp),
        onAttachmentsSelected = { attachments ->
            attachmentsPickerViewModel.changeAttachmentState(false)
            composerViewModel.addSelectedAttachments(attachments)
        },
        onDismiss = {
            attachmentsPickerViewModel.changeAttachmentState(false)
            attachmentsPickerViewModel.dismissAttachments()
        }
    )
}

@Composable
fun SelectedMessageMenu(
    selectedMessageState: SelectedMessageOptionsState,
    listViewModel: MessageListViewModel,
    composerViewModel: MessageComposerViewModel,
    user: User?,
    modifier: Modifier
) {
    val selectedMessage = selectedMessageState.message
    SelectedMessageMenu(
        modifier = modifier,
        message = selectedMessage,
        messageOptions = defaultMessageOptionsState(
            selectedMessage,
            user,
            listViewModel.isInThread,
            selectedMessageState.ownCapabilities
        ),
        ownCapabilities = selectedMessageState.ownCapabilities,
        onMessageAction = { action ->
            composerViewModel.performMessageAction(action)
            listViewModel.performMessageAction(action)
        },
        onShowMoreReactionsSelected = { listViewModel.selectExtendedReactions(selectedMessage) },
        onDismiss = { listViewModel.removeOverlay() }
    )
}
