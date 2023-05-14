package cz.cvut.fukalhan.swap.profile.presentation.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.data.resolve
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserLikedItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LikedItemListViewModel(
    private val getUserLikedItemsUseCase: GetUserLikedItemsUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase
) : ViewModel() {
    private val _itemListState: MutableStateFlow<ItemListState> = MutableStateFlow(Init)
    val itemListState: StateFlow<ItemListState>
        get() = _itemListState

    fun getLikedItems(uid: String) {
        _itemListState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getUserLikedItemsUseCase.getUserLikedItems(uid).resolve(
                onSuccess = { items ->
                    if (items.isNotEmpty()) {
                        _itemListState.value = Success(
                            items.map {
                                it.toItemState()
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
}
