package cz.cvut.fukalhan.swap.profile.presentation.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.data.resolve
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserItemsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserItemsViewModel(private val getUserItemsUseCase: GetUserItemsUseCase) : ViewModel() {
    private val _itemListState: MutableStateFlow<ItemListState> = MutableStateFlow(Init)
    val itemListState: StateFlow<ItemListState>
        get() = _itemListState

    fun getUserItems(uid: String) {
        _itemListState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            getUserItemsUseCase.getUserItems(uid).resolve(
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
}
