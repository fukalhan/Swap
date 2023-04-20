package cz.cvut.fukalhan.swap.profile.presentation.items

import cz.cvut.fukalhan.swap.itemdata.model.Item

internal fun Item.toItemState(): ItemState {
    return ItemState(
        this.id,
        this.imagesUri.first(),
        this.name
    )
}
