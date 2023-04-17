package cz.cvut.fukalhan.swap.profile.presentation.useritems

import cz.cvut.fukalhan.swap.itemdata.model.Item

internal fun Item.toItemState(): ItemState {
    return ItemState(
        this.imagesUri.first(),
        this.name
    )
}
