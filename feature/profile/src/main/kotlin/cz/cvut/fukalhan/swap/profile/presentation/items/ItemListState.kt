package cz.cvut.fukalhan.swap.profile.presentation.items

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.profile.R

sealed class ItemListState

object Init : ItemListState()
object Loading : ItemListState()

class Success(
    val items: List<ItemState> = emptyList()
) : ItemListState()

data class ItemState(
    val id: String,
    val imageUri: Uri,
    val name: String,
    val state: State
)

internal fun Item.toItemState(): ItemState {
    return ItemState(
        this.id,
        this.imagesUri.first(),
        this.name,
        this.state
    )
}

class Failure(val message: Int = R.string.cannotLoadItems) : ItemListState()
