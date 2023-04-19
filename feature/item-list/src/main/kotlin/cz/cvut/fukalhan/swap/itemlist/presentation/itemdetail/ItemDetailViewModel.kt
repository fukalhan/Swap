package cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemDetailViewModel(private val getItemDetailUseCase: GetItemDetailUseCase) : ViewModel() {
    private val _itemDetailState: MutableStateFlow<ItemDetailState> = MutableStateFlow(Init)
    val itemDetailState: StateFlow<ItemDetailState>
        get() = _itemDetailState

    fun getItemDetail(id: String) {
        _itemDetailState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemDetailUseCase.getItemDetail(id)
            response.data?.let { item ->
                _itemDetailState.value = item.toItemDetailState()
            } ?: run {
                _itemDetailState.value = Failure()
            }
        }
    }
}
