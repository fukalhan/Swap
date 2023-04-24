package cz.cvut.fukalhan.swap.messages.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.ChangeItemStateUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemFromChannelUseCase
import cz.cvut.fukalhan.swap.itemdata.model.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(
    private val getItemFromChannelUseCase: GetItemFromChannelUseCase,
    private val changeItemStateUseCase: ChangeItemStateUseCase
) : ViewModel() {
    private val _itemViewState: MutableStateFlow<ItemViewState> = MutableStateFlow(Init)
    val itemViewState: StateFlow<ItemViewState>
        get() = _itemViewState

    fun getItemData(channelId: String) {
        _itemViewState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getItemFromChannelUseCase.getItem(channelId)
            response.data?.let { item ->
                _itemViewState.value = item.toItemState()
            } ?: run {
                _itemViewState.value = Failure()
            }
        }
    }

    fun changeItemState(itemId: String, state: State, channelId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = changeItemStateUseCase.changeItemState(itemId, state)
            if (response.flag == ResponseFlag.SUCCESS) {
                _itemViewState.value = ChangeStateSuccess
            } else {
                _itemViewState.value = ChangeStateFailure()
            }
            getItemData(channelId)
        }
    }
}
