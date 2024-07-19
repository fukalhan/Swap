package cz.cvut.fukalhan.swap.itemlist.model

import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Sorting

/**
 * Events for ItemsSearchScreen
 */
sealed interface ItemsSearchScreenEvent {

    /**
     * Event to navigate back
     */
    data object OnBackClick : ItemsSearchScreenEvent

    /**
     * Event on search query input updated
     *
     * @property newValue new search input value
     */
    data class OnSearchQueryChange(val newValue: String) : ItemsSearchScreenEvent

    /**
     * Event on items sorting changed
     *
     * @property newSorting new sorting type
     */
    data class OnSortingChange(val newSorting: Sorting) : ItemsSearchScreenEvent

    /**
     * Event to open/close category picker bottom sheet
     *
     * @property visible determine bottom sheet visibility
     */
    data class ChangeCategoryBottomSheetVisibility(val visible: Boolean) : ItemsSearchScreenEvent

    /**
     * Event on category updated
     *
     * @property newCategory new selected category
     */
    data class OnCategoryChange(val newCategory: Category?) : ItemsSearchScreenEvent

    /**
     * Event on search canceled
     */
    data object OnCancelSearchClick : ItemsSearchScreenEvent

    /**
     * Event on search button clicked
     */
    data object OnSearchClick : ItemsSearchScreenEvent
}