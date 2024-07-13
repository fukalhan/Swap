package cz.cvut.fukalhan.swap.additem.viewmodel

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
import cz.cvut.fukalhan.design.presentation.showLoading
import cz.cvut.fukalhan.swap.additem.mapper.toDomain
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
import cz.cvut.fukalhan.swap.additem.view.AddItemScreen

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
            is AddItemScreenEvent.AddItemImages -> updateItemImages(event.uris)
            is AddItemScreenEvent.RemoveItemImage -> removeItemImage(event.uri)
            is AddItemScreenEvent.ItemNameUpdate -> updateName(event.name)
            is AddItemScreenEvent.ItemDescriptionUpdate -> updateDescription(event.description)
            is AddItemScreenEvent.ItemCategoryUpdate -> updateCategory(event.category)
            AddItemScreenEvent.OnSaveClick -> saveItem()
            is AddItemScreenEvent.ChangeCategoryBottomSheetVisibility -> changeCategoryBottomSheetVisibility(visible = event.visible)
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
     * Update item's description
     *
     * @param description new item description
     */
    private fun updateDescription(description: String) {
        if (description.length <= AddItemScreenData.DESCRIPTION_CHAR_LIMIT) {
            _viewState.update {
                UiState(
                    data = it.data.copy(
                        description = description
                    )
                )
            }
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
                        category = category,
                        showCategoryBottomSheet = false
                    )
                )
            }
        }
    }

    /**
     * Open/close category bottom sheet
     *
     * @param visible determine if the bottom sheet is visible/not visible
     */
    private fun changeCategoryBottomSheetVisibility(visible: Boolean) {
        _viewState.update {
            UiState(
                data = it.data.copy(
                    showCategoryBottomSheet = visible
                )
            )
        }
    }

    /**
     * Save item data
     */
    private fun saveItem() {
        val user = Firebase.auth.currentUser

        if (user != null && viewState.value.data.allFieldsFilled) {
            _viewState.showLoading()

            viewModelScope.launch(Dispatchers.IO) {
                saveItemUseCase
                    .saveItem(
                        viewState.value.data.toDomain(user.uid)
                    )
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
