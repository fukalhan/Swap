package cz.cvut.fukalhan.swap.messages.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemFromChannelUseCase
import cz.cvut.fukalhan.swap.itemdata.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val getItemFromChannelUseCase: GetItemFromChannelUseCase) : ViewModel() {
    private val _itemState: MutableStateFlow<ItemState> = MutableStateFlow(Init)
    val itemState: StateFlow<ItemState>
        get() = _itemState

    fun getItemData(channelId: String) {
        _itemState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemFromChannelUseCase.getItem(channelId)
            response.data?.let { item ->
                _itemState.value = item.toItemState()
            } ?: run {
                _itemState.value = Failure()
            }
        }
    }

    fun changeItemState(itemId: String, state: State) {
    }
}
