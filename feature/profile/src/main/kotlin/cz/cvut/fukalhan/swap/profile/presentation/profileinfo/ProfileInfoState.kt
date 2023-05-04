package cz.cvut.fukalhan.swap.profile.presentation.profileinfo

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class ProfileInfoState

object Init : ProfileInfoState()
object Loading : ProfileInfoState()

class Success(
    val id: String,
    val profilePicUri: Uri,
    val username: String,
    val joinDate: String,
    val rating: Float
) : ProfileInfoState()

class Failure(val message: Int = R.string.cannotLoadData) : ProfileInfoState()

fun User.toProfileInfoState(stringResources: StringResources): ProfileInfoState {
    return Success(
        this.id,
        this.profilePicUri,
        this.username,
        stringResources.getString(R.string.memberSince, formatDate(this.joinDate)),
        this.rating
    )
}
