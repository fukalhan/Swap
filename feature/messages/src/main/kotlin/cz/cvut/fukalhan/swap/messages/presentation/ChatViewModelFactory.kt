package cz.cvut.fukalhan.swap.messages.presentation

import android.content.Context
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.api.models.querysort.QuerySortByField
import io.getstream.chat.android.compose.viewmodel.channels.ChannelViewModelFactory
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

class ChatViewModelFactory(
    private val context: Context,
    private val chatClient: ChatClient
) {

    fun createFactory(channelId: String): MessagesViewModelFactory {
        return MessagesViewModelFactory(
            context = context,
            chatClient = chatClient,
            channelId = channelId
        )
    }

    fun createChannelsFactory(): ChannelViewModelFactory {
        return ChannelViewModelFactory(
            chatClient = chatClient,
            querySort = QuerySortByField.descByName("last_updated"),
            filters = null
        )
    }
}
