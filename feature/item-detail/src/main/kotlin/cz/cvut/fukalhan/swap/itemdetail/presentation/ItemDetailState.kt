package cz.cvut.fukalhan.swap.itemdetail.presentation

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class ItemDetailState

object Init : ItemDetailState()
object Loading : ItemDetailState()

class Success(
    val id: String,
    val name: String,
    val description: String,
    val images: List<Uri>,
    val category: Category,
    val state: State,
    val isLiked: Boolean,
    val ownerInfo: OwnerInfo
) : ItemDetailState()

data class OwnerInfo(
    val id: String,
    val profilePic: Uri,
    val username: String,
    val joinDate: String,
    val rating: Float
)

internal fun User.toOwnerInfo(stringResources: StringResources): OwnerInfo {
    return OwnerInfo(
        this.id,
        this.profilePicUri,
        this.username,
        stringResources.getString(R.string.memberSince, formatDate(this.joinDate)),
        this.rating
    )
}

internal fun ItemDetail.toItemDetailState(ownerInfo: OwnerInfo): Success {
    return Success(
        this.id,
        this.name,
        this.description,
        this.imagesUri,
        this.category,
        this.state,
        this.isLiked,
        ownerInfo
    )
}

class Failure(val message: Int = R.string.cannotLoadItemDetail) : ItemDetailState()

class CreateChannelSuccess(val channelId: String) : ItemDetailState()

class CreateChannelFailure(val message: Int = R.string.createChannelFailure) : ItemDetailState()
