package cz.cvut.fukalhan.swap.additem.model

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Category

/**
 * View data for AddItemScreen
 *
 * @param selectedImages item images
 * @param name name of the item
 * @param description item description
 * @param category item category
 * @param imagesLimit limit of item images
 */
data class AddItemScreenData(
    val selectedImages: List<Uri> = emptyList(),
    val name: String = "",
    val description: String = "",
    val category: Category? = null,
    val imagesLimit: Int = 6
)