package cz.cvut.fukalhan.swap.itemlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.data.resolve
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.SearchItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.itemdata.model.SearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase,
    private val searchItemsUseCase: SearchItemsUseCase
) : ViewModel() {
    private val _itemListState: MutableStateFlow<ItemListState> = MutableStateFlow(Init)
    val itemListState: StateFlow<ItemListState>
        get() = _itemListState

    fun getItems(uid: String) {
        _itemListState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getItemsUseCase.getItems(uid).resolve(
                onSuccess = {
                    val items = it.first
                    val likedItemsId = it.second
                    if (items.isNotEmpty()) {
                        _itemListState.value =
                            Success(
                                items.map { item ->
                                    item.toItemState(likedItemsId.contains(item.id))
                                }
                            )
                    } else {
                        _itemListState.value = Empty()
                    }
                },
                onError = { _itemListState.value = Failure() }
            )
        }
    }

    fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleItemLikeUseCase.toggleItemLike(userId, itemId, isLiked)
        }
    }

    fun searchItems(userId: String, searchQuery: SearchQuery) {
        _itemListState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            searchItemsUseCase.getSearchedItems(userId, searchQuery).resolve(
                onSuccess = {
                    val items = it.first
                    val likedItemsId = it.second
                    if (items.isNotEmpty()) {
                        _itemListState.value =
                            Success(
                                items.map { item ->
                                    item.toItemState(likedItemsId.contains(item.id))
                                }
                            )
                    } else {
                        _itemListState.value = Empty()
                    }
                },
                onError = { _itemListState.value = Failure() }
            )
        }
    }
}
