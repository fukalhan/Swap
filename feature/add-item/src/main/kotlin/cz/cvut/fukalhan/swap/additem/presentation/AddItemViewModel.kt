package cz.cvut.fukalhan.swap.additem.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.additem.model.AddItemScreenData
import cz.cvut.fukalhan.swap.additem.model.AddItemScreenEvent
import cz.cvut.fukalhan.swap.itemdata.data.resolve
import cz.cvut.fukalhan.swap.itemdata.domain.SaveItemUseCase
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import cz.cvut.fukalhan.swap.additem.system.AddItemScreen

class AddItemViewModel(
    private val saveItemUseCase: SaveItemUseCase
) : ViewModel() {

    private val _addItemState: MutableStateFlow<AddItemState> = MutableStateFlow(Init)

    val addItemState: StateFlow<AddItemState> = _addItemState.asStateFlow()

    private val _viewState: MutableStateFlow<AddItemScreenData> = MutableStateFlow(
        AddItemScreenData()
    )

    val viewState: StateFlow<AddItemScreenData> = _viewState.asStateFlow()

    /**
     * Handle [AddItemScreen] events
     */
    fun onEvent(event: AddItemScreenEvent) {
        when(event) {
            is AddItemScreenEvent.ItemCategoryUpdate -> updateCategory(event.category)
            is AddItemScreenEvent.ItemDescriptionUpdate -> updateDescription(event.description)
            is AddItemScreenEvent.AddItemImages -> updateItemImages(event.uris)
            is AddItemScreenEvent.ItemNameUpdate -> updateName(event.name)
            AddItemScreenEvent.OnSaveClick -> saveItem()
            is AddItemScreenEvent.RemoveItemImage -> removeItemImage(event.uri)
        }
    }

    /**
     * Update item's category
     *
     * @param category new item category
     */
    private fun updateCategory(category: Category?) {
        category?.let {
            _viewState.update {
                viewState.value.copy(
                    category = category
                )
            }
        }
    }

    /**
     * Update item's description
     *
     * @param description new item description
     */
    private fun updateDescription(description: String) {
        _viewState.update {
            viewState.value.copy(
                description = description
            )
        }
    }

    /**
     * Update item's images
     *
     * @param uris new list of item images uri
     */
    private fun updateItemImages(uris: List<Uri>) {
        val itemImages = viewState.value.selectedImages
        val imagesLimit = viewState.value.imagesLimit

        if (itemImages.size < imagesLimit) {
            _viewState.update {
                viewState.value.copy(
                    selectedImages = itemImages.take(imagesLimit) + uris.take(imagesLimit - itemImages.size)
                )
            }
        }
    }

    /**
     * Remove image from the item images
     *
     * @param uri uri of the image to be removed
     */
    private fun removeItemImage(uri: Uri) {
        val updatedImages = viewState.value.selectedImages.filter {
            it != uri
        }

        _viewState.update {
            viewState.value.copy(
                selectedImages = updatedImages
            )
        }
    }

    /**
     * Update item's name
     *
     * @param name new item name
     */
    private fun updateName(name: String) {
        _viewState.update {
            viewState.value.copy(
                name = name
            )
        }
    }

    /**
     * Save item data
     */
    private fun saveItem() {
        val itemData = viewState.value
        val user = Firebase.auth.currentUser

        if (user != null && itemData.name.isNotEmpty()
            && itemData.description.isNotEmpty() && itemData.category != null) {
            _addItemState.value = Loading

            viewModelScope.launch(Dispatchers.IO) {
                val item = Item(
                    ownerId = user.uid,
                    name = itemData.name,
                    description = itemData.description,
                    imagesUri = itemData.selectedImages,
                    category = itemData.category
                )

                saveItemUseCase.saveItem(item).resolve(
                    onSuccess = { _addItemState.value = Success() },
                    onError = { _addItemState.value = Failure() }
                )
            }
        }
    }

    fun setStateToInit() {
        _addItemState.value = Init
    }
}
