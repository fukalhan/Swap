package cz.cvut.fukalhan.swap.itemlist.presentation.message

import androidx.lifecycle.ViewModel
import io.getstream.chat.android.client.ChatClient

class MessageViewModel(
    private val chatClient: ChatClient
) : ViewModel()
