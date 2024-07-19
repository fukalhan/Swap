package cz.cvut.fukalhan.swap.itemlist.viewmodel

import androidx.lifecycle.ViewModel
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Sorting
import cz.cvut.fukalhan.swap.itemlist.model.ItemsSearchScreenData
import cz.cvut.fukalhan.swap.itemlist.model.ItemsSearchScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ItemSearchViewModel(
    private val params: Params,
) : ComposeViewModel<ItemsSearchScreenData, ItemsSearchScreenEvent>,
    ViewModel() {

    private val _viewState = MutableStateFlow(
        UiState(
            data = ItemsSearchScreenData()
        )
    )
    override val viewState: StateFlow<UiState<ItemsSearchScreenData>> = _viewState.asStateFlow()

    override fun onEvent(event: ItemsSearchScreenEvent) {
        when (event) {
            ItemsSearchScreenEvent.OnBackClick -> params.navigateBack
            is ItemsSearchScreenEvent.OnSearchQueryChange -> onSearchQueryChange(newValue = event.newValue)
            is ItemsSearchScreenEvent.OnSortingChange -> changeSorting(newSorting = event.newSorting)
            is ItemsSearchScreenEvent.OnCategoryChange -> changeCategory(newCategory = event.newCategory)
            ItemsSearchScreenEvent.OnCancelSearchClick -> params.navigateBack
            ItemsSearchScreenEvent.OnSearchClick -> filterItems()
            is ItemsSearchScreenEvent.ChangeCategoryBottomSheetVisibility ->
                changeCategoryBottomSheetVisibility(isVisible = event.visible)
        }
    }

    /**
     * Update search query
     */
    private fun onSearchQueryChange(newValue: String) {
        _viewState.update {
            UiState(
                data = it.data.copy(
                    searchInput = newValue
                )
            )
        }
    }

    /**
     * Change items sorting
     */
    private fun changeSorting(newSorting: Sorting) {
        _viewState.update {
            UiState(
                data = it.data.copy(
                    sorting = newSorting
                )
            )
        }
    }

    /**
     * Update picked category
     */
    private fun changeCategory(newCategory: Category?) {
        newCategory?.let { category ->
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
     * Filter items according to criteria
     */
    private fun filterItems() {
        // TODO
    }

    /**
     * Show/hide category bottom sheet
     *
     * @param isVisible determine if the bottom sheet should open/close
     */
    private fun changeCategoryBottomSheetVisibility(isVisible: Boolean) {
        _viewState.update {
            UiState(
                data = it.data.copy(
                    showCategoryBottomSheet = isVisible
                )
            )
        }
    }

    data class Params(
        val navigateBack: () -> Unit
    )
}