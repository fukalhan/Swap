package cz.cvut.fukalhan.swap.itemdetail.model

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.State

/**
 * Screen data for the ItemDetailScreen
 *
 * @property id item id
 * @property name item name
 * @property description the item description
 * @property images item images
 * @property category item category
 * @property state item state, determines if it's available, reserved or swapped
 * @property isLiked determines if the current user has added the item to his favourites
 * @property ownerInfoVo info about the item's owner
 */
data class ItemDetailScreenData(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val images: List<Uri> = emptyList(),
    val category: Category = Category.OTHER,
    val state: State = State.AVAILABLE,
    val isLiked: Boolean = false,
    val ownerInfoVo: OwnerInfoVo = OwnerInfoVo()
)