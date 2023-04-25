package cz.cvut.fukalhan.swap.messages.presentation

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.messages.R

sealed class ItemViewState

object Init : ItemViewState()

object Loading : ItemViewState()

data class Success(
    val id: String,
    val name: String,
    val image: Uri,
    val state: State,
    val ownerId: String
) : ItemViewState()

fun Item.toItemState(): ItemViewState {
    return Success(
        this.id,
        this.name,
        this.imagesUri.first(),
        this.state,
        this.ownerId
    )
}

data class Failure(val message: Int = R.string.cannotLoadItemData) : ItemViewState()

object ChangeStateSuccess : ItemViewState()
data class ChangeStateFailure(val message: Int = R.string.cannotChangeItemState) : ItemViewState()
