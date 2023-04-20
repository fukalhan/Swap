package cz.cvut.fukalhan.swap.itemlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val getItemsUseCase: GetItemsUseCase,
    private val toggleItemLikeUseCase: ToggleItemLikeUseCase
) : ViewModel() {
    private val _itemListState: MutableStateFlow<ItemListState> = MutableStateFlow(Init)
    val itemListState: StateFlow<ItemListState>
        get() = _itemListState

    init {
        val user = Firebase.auth.currentUser
        user?.let {
            getItems(it.uid)
        }
    }

    private fun getItems(uid: String) {
        _itemListState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemsUseCase.getItems(uid)
            response.data?.let { items ->
                _itemListState.value = Success(
                    items.map {
                        it.toItemState()
                    }
                )
            } ?: run {
                _itemListState.value = Failure()
            }
        }
    }

    fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = toggleItemLikeUseCase.toggleItemLike(userId, itemId, isLiked)
        }
    }
}
