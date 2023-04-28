package cz.cvut.fukalhan.swap.profiledetail.presentation.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getUserItemsUseCase: GetUserItemsUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase
) : ViewModel() {
    private val _itemListState: MutableStateFlow<ItemListState> = MutableStateFlow(ItemListState.Init)
    val itemListState: StateFlow<ItemListState>
        get() = _itemListState

    fun getUserItems(userId: String) {
        _itemListState.value = ItemListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getUserItemsUseCase.getUserItems(userId)
            response.data?.let { items ->
                if (items.isNotEmpty()) {
                    _itemListState.value = ItemListState.DataLoaded(
                        items.map {
                            it.toItemState()
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

    fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = toggleItemLikeUseCase.toggleItemLike(userId, itemId, isLiked)
            if (response.success) {
                getUserItems(userId)
            }
        }
    }
}
