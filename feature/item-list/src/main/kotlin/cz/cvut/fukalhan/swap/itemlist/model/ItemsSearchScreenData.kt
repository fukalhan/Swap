package cz.cvut.fukalhan.swap.itemlist.model

import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Sorting

/**
 * View object for ItemsSearchScreen
 *
 * @property searchInput search query
 * @property sorting the type of sorting of the items
 * @property category currently selected search category, if null we search throughout
 * all categories
 * @property showCategoryBottomSheet determine if pick search category bottom sheet is visible
 */
data class ItemsSearchScreenData(
    val searchInput: String = "",
    val sorting: Sorting = Sorting.DEFAULT,
    val category: Category? = null,
    val showCategoryBottomSheet: Boolean = false
)