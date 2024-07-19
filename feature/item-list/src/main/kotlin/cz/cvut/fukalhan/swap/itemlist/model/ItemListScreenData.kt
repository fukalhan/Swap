package cz.cvut.fukalhan.swap.itemlist.model

import cz.cvut.fukalhan.swap.itemlist.view.ItemListScreen

/**
 * View object for the [ItemListScreen]
 *
 * @property items items to be displayed on the screen
 */
data class ItemListScreenData(
    val items: List<ItemVo> = emptyList()
)