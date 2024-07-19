package cz.cvut.fukalhan.swap.itemdetail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.PRIVATE_CHAT
import cz.cvut.fukalhan.design.presentation.ResultModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.presentation.hideAllOverlays
import cz.cvut.fukalhan.design.presentation.showLoading
import cz.cvut.fukalhan.swap.itemdata.data.resolve
import cz.cvut.fukalhan.swap.itemdata.domain.CreateChannelUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemDetailUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.itemdata.model.Channel
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.swap.itemdetail.mapper.toItemDetailScreenData
import cz.cvut.fukalhan.swap.itemdetail.model.ItemDetailScreenData
import cz.cvut.fukalhan.swap.itemdetail.model.ItemDetailScreenEvent
import cz.cvut.fukalhan.swap.userdata.data.resolve
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val args: Args,
    private val chatClient: ChatClient,
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase,
    private val createChannelUseCase: CreateChannelUseCase,
) : ComposeViewModel<ItemDetailScreenData, ItemDetailScreenEvent>,
    ViewModel() {

    private val _viewState = MutableStateFlow(
        UiState(
            data = ItemDetailScreenData(),
            loading = true
        )
    )

    override val viewState: StateFlow<UiState<ItemDetailScreenData>> = _viewState.asStateFlow()

    override fun onEvent(event: ItemDetailScreenEvent) {
        when (event) {
            is ItemDetailScreenEvent.OnLikeClick -> changeItemLikeState(event.isLiked)
            ItemDetailScreenEvent.CreateChatChannel -> createChannel()
        }
    }

    init {
        Firebase.auth.currentUser?.let {
            getItemDetail(
                userId = it.uid,
                itemId = args.itemId
            )
        }
    }

    /**
     * Retrieve item data with the information about the owner user
     *
     * @param userId id of the current user
     * @param itemId id of the item which detail should be retrieved
     */
    private fun getItemDetail(
        userId: String,
        itemId: String
    ) {
        _viewState.showLoading()

        viewModelScope.launch(Dispatchers.IO) {
            getItemDetailUseCase.getItemDetail(userId, itemId).resolve(
                onSuccess = { itemDetail ->
                    this.launch {
                        getUserDataUseCase.getUserData(itemDetail.ownerId).resolve(
                            onSuccess = { user ->
                                _viewState.update {
                                    UiState(
                                        data = itemDetail.toItemDetailScreenData(owner = user)
                                    )
                                }
                            },
                            onError = {
                                showError(message = R.string.load_item_detail_error)
                            }
                        )
                    }
                },
                onError = {
                    showError(message = R.string.load_item_detail_error)
                }
            )
        }
    }


    /**
     * Change the like state of the item
     *
     * @param isLiked new item like value
     */
    private fun changeItemLikeState(isLiked: Boolean) {
        Firebase.auth.currentUser?.let { user ->
            _viewState.showLoading()

            viewModelScope.launch(Dispatchers.IO) {
                toggleItemLikeUseCase.toggleItemLike(
                    userId = user.uid,
                    itemId = viewState.value.data.id,
                    isLiked = isLiked
                ).resolve(
                    onSuccess = {
                        _viewState.update {
                            UiState(
                                data = it.data.copy(
                                    isLiked = isLiked
                                )
                            )
                        }
                    },
                    onError = {
                        showError(message = R.string.item_like_error)
                    }
                )
            }
        } ?: showError(message = R.string.item_like_error)
    }

    /**
     * Create messages chat channel between the current user and the owner of the item
     */
    private fun createChannel() {
        Firebase.auth.currentUser?.let {
            _viewState.showLoading()

            viewModelScope.launch(Dispatchers.IO) {
                createChannelUseCase.createChannel(
                    channel = Channel(
                        userId = it.uid,
                        itemId = viewState.value.data.id,
                        ownerId = viewState.value.data.ownerInfoVo.id
                    )
                ).resolve(
                    onSuccess = { channelId ->
                        chatClient.createChannel(
                            channelId = channelId,
                            channelType = PRIVATE_CHAT,
                            memberIds = listOf(it.uid, viewState.value.data.ownerInfoVo.id),
                            extraData = mapOf(
                                "name" to "Předmět: ${viewState.value.data.name}",
                                "image" to viewState.value.data.images.first().toString()
                            )
                        ).enqueue { result ->
                            if (result.isSuccess) {
                                Log.e("CreateChannel", result.toString())
                                _viewState.hideAllOverlays()
                                // TODO navigate to new channel screen
                            } else {
                                Log.e("CreateChannel", result.toString())
                                showError(message = R.string.create_chat_channel_error)
                            }
                        }
                    },
                    onError = {
                        showError(message = R.string.create_chat_channel_error)
                    }
                )
            }
        } ?: showError(message = R.string.create_chat_channel_error)
    }

    /**
     * Helper function to display error message when there is an error
     */
    private fun showError(message: Int) {
        _viewState.update {
            it.copy(
                loading = false,
                resultModel = ResultModel.Error(
                    message = StringModel.Resource(id = message)
                )
            )
        }
    }

    data class Args(
        val itemId: String
    )
}
