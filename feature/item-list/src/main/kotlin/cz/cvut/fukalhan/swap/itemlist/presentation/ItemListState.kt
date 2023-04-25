package cz.cvut.fukalhan.swap.itemlist.presentation

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.itemlist.R

sealed class ItemListState

object Init : ItemListState()
object Loading : ItemListState()
data class Success(
    val items: List<ItemState> = emptyList()
) : ItemListState()

data class ItemState(
    val id: String,
    val imageUri: Uri,
    val name: String,
    val state: State,
    val isLiked: Boolean
)

class Empty(val message: Int = R.string.noItemsToDisplay) : ItemListState()
class Failure(val message: Int = R.string.cannotLoadItems) : ItemListState()

internal fun Item.toItemState(isLiked: Boolean): ItemState {
    return ItemState(
        this.id,
        this.imagesUri.first(),
        this.name,
        this.state,
        isLiked
    )
}
