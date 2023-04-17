package cz.cvut.fukalhan.swap.additem.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.swap.itemdata.domain.SaveItemUseCase
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddItemViewModel(private val saveItemUseCase: SaveItemUseCase) : ViewModel() {
    private val _saveItemState = MutableStateFlow<AddItemState>(PendingState)
    val saveItemState: StateFlow<AddItemState>
        get() = _saveItemState

    fun saveItem(
        ownerId: String,
        name: String,
        description: String,
        imagesUri: List<Uri>,
        category: Category
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = Item(
                ownerId = ownerId,
                name = name,
                description = description,
                imagesUri = imagesUri,
                category = category
            )
            val saveItemResponse = saveItemUseCase.saveItem(item)
            _saveItemState.value = saveItemResponse.toAddItemState()
        }
    }

    fun neutralizeItemState() {
        _saveItemState.value = PendingState
    }
}
