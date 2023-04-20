package cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemDetailUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase
) : ViewModel() {
    private val _itemDetailState: MutableStateFlow<ItemDetailState> = MutableStateFlow(Init)
    val itemDetailState: StateFlow<ItemDetailState>
        get() = _itemDetailState

    fun getItemDetail(userId: String, itemId: String) {
        _itemDetailState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemDetailUseCase.getItemDetail(userId, itemId)
            response.data?.let { item ->
                _itemDetailState.value = item.toItemDetailState()
            } ?: run {
                _itemDetailState.value = Failure()
            }
        }
    }

    fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = toggleItemLikeUseCase.toggleItemLike(userId, itemId, isLiked)
        }
    }
}
