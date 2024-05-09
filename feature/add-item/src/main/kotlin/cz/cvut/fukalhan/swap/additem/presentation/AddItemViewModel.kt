package cz.cvut.fukalhan.swap.additem.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.ResultModel
import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.presentation.hideAllOverlays
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
) : ComposeViewModel<AddItemScreenData, AddItemScreenEvent>,
    ViewModel() {

    private val _viewState: MutableStateFlow<UiState<AddItemScreenData>> = MutableStateFlow(
        UiState(
            data = AddItemScreenData()
        )
    )

    override val viewState: StateFlow<UiState<AddItemScreenData>> = _viewState.asStateFlow()

    /**
     * Handle [AddItemScreen] events
     */
    override fun onEvent(event: AddItemScreenEvent) {
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
                UiState(
                    data = it.data.copy(
                        category = category
                    )
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
            UiState(
                data = it.data.copy(
                    description = description
                )
            )
        }
    }

    /**
     * Update item's images
     *
     * @param uris new list of item images uri
     */
    private fun updateItemImages(uris: List<Uri>) {
        val itemImages = viewState.value.data.selectedImages
        val imagesLimit = viewState.value.data.imagesLimit

        if (itemImages.size < imagesLimit) {
            _viewState.update {
                UiState(
                    data = it.data.copy(
                        selectedImages = itemImages.take(imagesLimit) + uris.take(imagesLimit - itemImages.size)
                    )
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
        val updatedImages = viewState.value.data.selectedImages.filter {
            it != uri
        }

        _viewState.update {
            UiState(
                data = it.data.copy(
                    selectedImages = updatedImages
                )
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
            UiState(
                data = it.data.copy(
                    name = name
                )
            )
        }
    }

    /**
     * Save item data
     */
    private fun saveItem() {
        val itemData = viewState.value.data
        val user = Firebase.auth.currentUser

        if (user != null && itemData.name.isNotEmpty()
            && itemData.description.isNotEmpty() && itemData.category != null) {
            _viewState.update {
                it.copy(
                    loading = true
                )
            }

            viewModelScope.launch(Dispatchers.IO) {
                val item = Item(
                    ownerId = user.uid,
                    name = itemData.name,
                    description = itemData.description,
                    imagesUri = itemData.selectedImages,
                    category = itemData.category
                )

                saveItemUseCase
                    .saveItem(item)
                    .resolve(
                    onSuccess = {
                        _viewState.hideAllOverlays()
                        _viewState.update {
                            it.copy(
                                resultModel = ResultModel.Success(
                                    StringModel.Resource(id = R.string.itemSaveSuccess)
                                )
                            )
                        }
                    },
                    onError = {
                        _viewState.hideAllOverlays()
                        _viewState.update {
                            it.copy(
                                resultModel = ResultModel.Error(
                                    StringModel.Resource(id = R.string.itemSaveFail)
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}
