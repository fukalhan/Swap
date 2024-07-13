package cz.cvut.fukalhan.swap.additem.model

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Category

/**
 * View data for AddItemScreen
 *
 * @property selectedImages item images
 * @property name name of the item
 * @property description item description
 * @property category item category
 * @property imagesLimit limit of item images
 * @property showCategoryBottomSheet determine if the category picker bottom sheet is open
 * @property isSaveButtonEnabled determine if the save button should be enabled
 * (when all fields are filled accordingly
 */
data class AddItemScreenData(
    val selectedImages: List<Uri> = emptyList(),
    val name: String = "",
    val description: String = "",
    val category: Category? = null,
    val imagesLimit: Int = 6,
    val showCategoryBottomSheet: Boolean = false
) {
    val isSaveButtonEnabled =
        selectedImages.isNotEmpty() && name.isNotBlank() && description.isNotBlank() && category != null

    companion object {
        const val DESCRIPTION_CHAR_LIMIT = 150
    }
}