package cz.cvut.fukalhan.swap.itemlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.presentation.hideAllOverlays
import cz.cvut.fukalhan.swap.itemdata.data.resolve
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.itemlist.mapper.toVo
import cz.cvut.fukalhan.swap.itemlist.model.ItemListScreenData
import cz.cvut.fukalhan.swap.itemlist.model.ItemListScreenEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val params: Params,
    private val getItemsUseCase: GetItemsUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase,
) : ComposeViewModel<ItemListScreenData, ItemListScreenEvent>,
    ViewModel() {

    private val _viewState = MutableStateFlow(
        UiState(
            data = ItemListScreenData(),
            loading = true
        )
    )

    override val viewState: StateFlow<UiState<ItemListScreenData>> = _viewState.asStateFlow()

    init {
        getItems()
    }

    override fun onEvent(event: ItemListScreenEvent) {
        when (event) {
            is ItemListScreenEvent.OnItemClick -> onItemClick(id = event.id)
            is ItemListScreenEvent.OnItemLikeClick -> changeItemLike(
                id = event.id,
                isLiked = event.liked
            )
            ItemListScreenEvent.OnSearchClick -> onSearchClick()
        }
    }

    /**
     * Load items data
     */
    private fun getItems() {
        Firebase.auth.currentUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getItemsUseCase
                    .getItems(it.uid)
                    .resolve(
                        onSuccess = {
                            val items = it.first
                            val likedItemsId = it.second
                            if (items.isNotEmpty()) {
                                _viewState.update {
                                    UiState(
                                        data = it.data.copy(
                                            items = items.map { item ->
                                                item.toVo(likedItemsId.contains(item.id))
                                            }
                                        )
                                    )
                                }
                            }
                            _viewState.hideAllOverlays()
                        },
                        onError = {
                            _viewState.hideAllOverlays()
                            // TODO display error view
                        }
                    )
            }
        }
    }

    /**
     * Function to navigate to item detail on item click
     *
     * @param id item id
     */
    private fun onItemClick(id: String) {
        params.navigateToItemDetail(id)
    }

    /**
     * On item's like button click, add/remove item to/from user's favourite
     *
     * @param id item id
     * @param isLiked determine if item was liked/remove from favourites
     */
    private fun changeItemLike(id: String, isLiked: Boolean) {
        Firebase.auth.currentUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                toggleItemLikeUseCase.toggleItemLike(
                    userId = it.uid,
                    itemId = id,
                    isLiked = isLiked
                ).resolve(
                    onSuccess = {
                        _viewState.update {
                            UiState(
                                data = it.data.copy(
                                    items = it.data.items.map { item ->
                                        if (item.id == id) {
                                            item.copy(
                                                isLiked = isLiked
                                            )
                                        } else {
                                            item
                                        }
                                    }
                                )
                            )
                        }
                    },
                    onError = {
                        // TODO display message
                    }
                )
            }
        }
    }

    /**
     * On item search click
     */
    private fun onSearchClick() {
        params.navigateToSearchScreen()
    }

    data class Params(
        val navigateToSearchScreen: () -> Unit,
        val navigateToItemDetail: (String) -> Unit
    )
}
