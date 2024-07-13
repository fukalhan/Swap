package cz.cvut.fukalhan.swap.additem.mapper

import cz.cvut.fukalhan.swap.additem.model.AddItemScreenData
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.additem.view.AddItemScreen

/**
 * Map VO data from [AddItemScreen] to domain object
 *
 * @param userId user id
 */
internal fun AddItemScreenData.toDomain(userId: String) =
    Item(
        ownerId = userId,
        name = name,
        description = description,
        imagesUri = selectedImages,
        category = category ?: Category.OTHER
    )