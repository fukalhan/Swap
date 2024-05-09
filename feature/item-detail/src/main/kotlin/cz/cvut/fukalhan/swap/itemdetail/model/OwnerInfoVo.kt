package cz.cvut.fukalhan.swap.itemdetail.model

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * View object for the item's owner info
 *
 * @property id user id
 * @property profilePic uri of the users profile pic, empty if none provided
 * @property username user username
 * @property joinDate the date when the user joined the platform
 * @property rating user's rating from his reviews
 */
data class OwnerInfoVo(
    val id: String = "",
    val profilePic: Uri = Uri.EMPTY,
    val username: String = "",
    val joinDate: StringModel = StringModel.Empty,
    val rating: Float = 0f
)