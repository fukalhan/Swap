package cz.cvut.fukalhan.swap.itemdetail.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.PRIVATE_CHAT
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.itemdata.domain.CreateChannelUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemDetailUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.itemdata.model.Channel
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val chatClient: ChatClient,
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase,
    private val createChannelUseCase: CreateChannelUseCase,
    private val stringResources: StringResources
) : ViewModel() {
    private val _itemDetailState: MutableStateFlow<ItemDetailState> = MutableStateFlow(Init)
    val itemDetailState: StateFlow<ItemDetailState>
        get() = _itemDetailState

    fun getItemDetail(userId: String, itemId: String) {
        _itemDetailState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getItemDetailUseCase.getItemDetail(userId, itemId).data?.let { itemDetail ->
                getUserDataUseCase.getUserData(itemDetail.ownerId).data?.let { user ->
                    val ownerInfo = user.toOwnerInfo(stringResources)
                    _itemDetailState.value = itemDetail.toItemDetailState(ownerInfo)
                } ?: run {
                    _itemDetailState.value = Failure()
                }
            } ?: run {
                _itemDetailState.value = Failure()
            }
        }
    }

    fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleItemLikeUseCase.toggleItemLike(userId, itemId, isLiked)
        }
    }

    fun createChannel(
        userId: String,
        itemOwnerId: String,
        itemId: String,
        itemImage: Uri,
        channelName: String,
    ) {
        _itemDetailState.value = Loading
        val channel = Channel(userId, itemId, itemOwnerId)
        viewModelScope.launch(Dispatchers.IO) {
            val response = createChannelUseCase.createChannel(channel)
            response.data?.let { channelId ->
                chatClient.createChannel(
                    channelId = channelId,
                    channelType = PRIVATE_CHAT,
                    memberIds = listOf(userId, itemOwnerId),
                    extraData = mapOf(
                        "name" to "Předmět: $channelName",
                        "image" to itemImage.toString()
                    )
                ).enqueue { result ->
                    if (result.isSuccess) {
                        Log.e("owjfoiw", result.toString())
                        _itemDetailState.value = CreateChannelSuccess(channelId)
                    } else {
                        Log.e("owjfoiw", result.toString())
                        _itemDetailState.value = CreateChannelFailure()
                        // TODO delete channel record from the db
                    }
                }
            } ?: run {
                _itemDetailState.value = CreateChannelFailure()
            }
        }
    }

    fun setStateToInit() {
        _itemDetailState.value = Init
    }
}
