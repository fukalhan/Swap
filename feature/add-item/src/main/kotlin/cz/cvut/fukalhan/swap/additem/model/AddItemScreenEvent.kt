package cz.cvut.fukalhan.swap.additem.model

import android.net.Uri
import cz.cvut.fukalhan.swap.additem.system.AddItemScreen
import cz.cvut.fukalhan.swap.itemdata.model.Category

/**
 * Events for [AddItemScreen]
 */
sealed interface AddItemScreenEvent {

    /**
     * Event on add new item image
     *
     * @param uris list of new item's images
     */
    data class AddItemImages(val uris: List<Uri>) : AddItemScreenEvent

    /**
     * Event on remove image from image picker
     *
     * @param uri uri of the image to be removed
     */
    data class RemoveItemImage(val uri: Uri) : AddItemScreenEvent

    /**
     * Event on item name changed
     *
     * @param name new item name
     */
    data class ItemNameUpdate(val name: String) : AddItemScreenEvent

    /**
     * Event on item description changed
     *
     * @param description new item description
     */
    data class ItemDescriptionUpdate(val description: String) : AddItemScreenEvent

    /**
     * Event on item category changed
     *
     * @param category new item category
     */
    data class ItemCategoryUpdate(val category: Category?) : AddItemScreenEvent

    /**
     * On save item data button click
     */
    data object OnSaveClick : AddItemScreenEvent
}