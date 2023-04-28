package cz.cvut.fukalhan.swap.profiledetail.presentation.items

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.profiledetail.R

sealed class ItemListState {
    object Init : ItemListState()

    object Loading : ItemListState()

    data class DataLoaded(
        val items: List<ItemState>
    ) : ItemListState()

    data class Empty(val message: Int = R.string.noItemsToDisplay) : ItemListState()

    data class Failure(val message: Int = R.string.cannotLoadItems) : ItemListState()
}

data class ItemState(
    val id: String,
    val imageUri: Uri,
    val name: String,
    val state: State,
    val isLiked: Boolean
)

internal fun Item.toItemState(isLiked: Boolean): ItemState {
    return ItemState(
        this.id,
        this.imagesUri.first(),
        this.name,
        this.state,
        isLiked
    )
}
