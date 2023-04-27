package cz.cvut.fukalhan.swap.itemdetail.presentation

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail
import cz.cvut.fukalhan.swap.itemdata.model.State
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.userdata.model.User
import java.text.SimpleDateFormat
import java.util.Locale

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
        stringResources.getString(R.string.memberSince, convertDateString(this.joinDate)),
        this.rating
    )
}

private fun convertDateString(dateString: String): String {
    val inputDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
    val outputDateFormat = SimpleDateFormat("d.M.yyyy", Locale.US)

    val date = inputDateFormat.parse(dateString)
    return outputDateFormat.format(date)
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
