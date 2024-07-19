package cz.cvut.fukalhan.swap.itemlist.mapper

import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemlist.model.ItemVo

/**
 * Mapping of domain [Item] to [ItemVo] view object
 *
 * @param isLiked determine if the item was liked by the user
 */
internal fun Item.toVo(isLiked: Boolean) =
    ItemVo(
        id = id,
        imageUri = imagesUri.first(),
        name = name,
        state = state,
        isLiked = isLiked
    )