package cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail
import cz.cvut.fukalhan.swap.itemdata.model.ItemState
import cz.cvut.fukalhan.swap.itemlist.R

sealed class ItemDetailState

object Init : ItemDetailState()
object Loading : ItemDetailState()

class Success(
    val id: String,
    val name: String,
    val description: String,
    val images: List<Uri>,
    val category: Category,
    val state: ItemState,
    val isLiked: Boolean
) : ItemDetailState()

internal fun ItemDetail.toItemDetailState(): Success {
    return Success(
        this.id,
        this.name,
        this.description,
        this.imagesUri,
        this.category,
        this.state,
        this.isLiked
    )
}

class Failure(val message: Int = R.string.cannotLoadItemDetail) : ItemDetailState()
