package cz.cvut.fukalhan.swap.messages.system

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.swap.messages.R
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme

@Composable
fun MessageScreen() {
    ChatTheme {
        ChannelsScreen(
            title = stringResource(R.string.messages),
            onItemClick = {
                // On item clicked action
            },
            onHeaderActionClick = {
                // Header header action clicks
            },
            onHeaderAvatarClick = {
                // Handle header avatar clicks
            },
            onBackPressed = { }
        )
    }
}
