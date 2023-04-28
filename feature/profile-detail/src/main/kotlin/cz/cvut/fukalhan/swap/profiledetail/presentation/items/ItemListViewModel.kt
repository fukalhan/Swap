package cz.cvut.fukalhan.swap.profiledetail.presentation.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserProfileItemsUserCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.itemdata.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getUserProfileItemsUserCase: GetUserProfileItemsUserCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase
) : ViewModel() {
    private val _itemListState: MutableStateFlow<ItemListState> = MutableStateFlow(ItemListState.Init)
    val itemListState: StateFlow<ItemListState>
        get() = _itemListState

    fun getUserItems(currentUserId: String, userId: String) {
        _itemListState.value = ItemListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserProfileItemsUserCase.getItems(currentUserId, userId)
            response.data?.let {
                val items = it.first
                val likedIds = it.second
                if (items.isNotEmpty()) {
                    _itemListState.value = ItemListState.DataLoaded(
                        items.map { item ->
                            item.toItemState(likedIds.contains(item.id))
                        }.filter { itemState ->
                            itemState.state != State.SWAPPED
                        }
                    )
                } else {
                    _itemListState.value = ItemListState.Empty()
                }
            } ?: run {
                _itemListState.value = ItemListState.Failure()
            }
        }
    }

    fun toggleItemLike(currentUserId: String, userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = toggleItemLikeUseCase.toggleItemLike(currentUserId, itemId, isLiked)
            if (response.success) {
                getUserItems(currentUserId, userId)
            }
        }
    }
}
