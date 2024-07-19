package cz.cvut.fukalhan.swap.itemlist.model

/**
 * Interface for all ItemListScreen events
 */
sealed interface ItemListScreenEvent {

    /**
     * Event on search button in top bar click
     */
    data object OnSearchClick : ItemListScreenEvent

    /**
     * Event on item click - navigate to item detail
     *
     * @property id item id
     */
    data class OnItemClick(val id: String) : ItemListScreenEvent

    /**
     * Event on item like click - like item
     *
     * @property id item id
     * @property liked new value of like of the item
     */
    data class OnItemLikeClick(val id: String, val liked: Boolean) : ItemListScreenEvent
}