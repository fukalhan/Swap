package cz.cvut.fukalhan.swap.additem.model

import android.net.Uri
import cz.cvut.fukalhan.swap.additem.view.AddItemScreen
import cz.cvut.fukalhan.swap.itemdata.model.Category

/**
 * Events for [AddItemScreen]
 */
sealed interface AddItemScreenEvent {

    /**
     * Event on add new item image
     *
     * @property uris list of new item's images
     */
    data class AddItemImages(val uris: List<Uri>) : AddItemScreenEvent

    /**
     * Event on remove image from image picker
     *
     * @property uri uri of the image to be removed
     */
    data class RemoveItemImage(val uri: Uri) : AddItemScreenEvent

    /**
     * Event on item name changed
     *
     * @property name new item name
     */
    data class ItemNameUpdate(val name: String) : AddItemScreenEvent

    /**
     * Event on item description changed
     *
     * @property description new item description
     */
    data class ItemDescriptionUpdate(val description: String) : AddItemScreenEvent

    /**
     * Event on item category changed
     *
     * @property category new item category
     */
    data class ItemCategoryUpdate(val category: Category?) : AddItemScreenEvent

    /**
     * Event to open/close category picker bottom sheet
     *
     * @property visible the bottom sheet visibility
     */
    data class ChangeCategoryBottomSheetVisibility(val visible: Boolean) : AddItemScreenEvent

    /**
     * On save item data button click
     */
    data object OnSaveClick : AddItemScreenEvent
}