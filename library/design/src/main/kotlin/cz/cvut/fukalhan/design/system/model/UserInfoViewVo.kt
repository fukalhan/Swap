package cz.cvut.fukalhan.design.system.model

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringModel

/**
 * View object for the UserInfoView component
 *
 * @property username profile username
 * @property profilePicUri uri of the profile picture
 * @property joinDate formatted string with the day the user joined the app
 * @property rating user's rating
 * @property endIcon optional end icon
 */
data class UserInfoViewVo(
    val username: String,
    val profilePicUri: Uri,
    val joinDate: StringModel,
    val rating: Float,
    val endIcon: Int? = null
)