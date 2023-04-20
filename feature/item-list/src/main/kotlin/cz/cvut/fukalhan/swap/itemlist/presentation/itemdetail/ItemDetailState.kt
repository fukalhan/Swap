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
    val isLiked: Boolean,
    val ownerInfo: OwnerInfo
) : ItemDetailState()

data class OwnerInfo(
    val id: String,
    val username: String,
    val profilePic: Uri
)
internal fun ItemDetail.toItemDetailState(ownerId: String, ownerUsername: String, ownerProfilePic: Uri): Success {
    return Success(
        this.id,
        this.name,
        this.description,
        this.imagesUri,
        this.category,
        this.state,
        this.isLiked,
        OwnerInfo(
            ownerId,
            ownerUsername,
            ownerProfilePic
        )
    )
}

class Failure(val message: Int = R.string.cannotLoadItemDetail) : ItemDetailState()
