package cz.cvut.fukalhan.swap.itemlist.model

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.State

/**
 * View object for displaying item
 *
 * @property id item id
 * @property imageUri uri of the item image
 * @property name item name
 * @property state item state - if it's reserved or already swapped
 * @property isLiked determine if user has this item added to favourites
 */
data class ItemVo(
    val id: String,
    val imageUri: Uri,
    val name: String,
    val state: State,
    val isLiked: Boolean
)