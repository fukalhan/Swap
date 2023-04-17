package cz.cvut.fukalhan.swap.itemlist.presentation

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Item

data class ItemListState(
    val items: List<ItemState> = emptyList()
)

data class ItemState(
    val imageUri: Uri,
    val name: String,
)

internal fun Item.toItemState(): ItemState {
    return ItemState(
        this.imagesUri.first(),
        this.name
    )
}
