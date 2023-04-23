package cz.cvut.fukalhan.swap.messages.presentation

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.messages.R

sealed class ItemState

object Init : ItemState()

object Loading : ItemState()

data class Success(
    val id: String,
    val name: String,
    val image: Uri,
    val state: State,
    val ownerId: String
) : ItemState()

fun Item.toItemState(): ItemState {
    return Success(
        this.id,
        this.name,
        this.imagesUri.first(),
        this.state,
        this.ownerId
    )
}

data class Failure(val message: Int = R.string.cannotLoadItemData) : ItemState()
