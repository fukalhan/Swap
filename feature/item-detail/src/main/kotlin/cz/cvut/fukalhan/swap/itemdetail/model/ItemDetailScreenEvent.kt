package cz.cvut.fukalhan.swap.itemdetail.model

import cz.cvut.fukalhan.swap.itemdetail.view.ItemDetailScreen

/**
 * Events for the [ItemDetailScreen]
 */
sealed interface ItemDetailScreenEvent {

    /**
     * Event on like button clicked
     *
     * @property isLiked new button value, determine if it is liked or not
     */
    data class OnLikeClick(val isLiked: Boolean) : ItemDetailScreenEvent

    /**
     * Event on send a message to the owner
     */
    data object CreateChatChannel : ItemDetailScreenEvent
}