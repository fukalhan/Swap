package cz.cvut.fukalhan.swap.review.presentation

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.swap.review.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class UserInfoState {
    object Init : UserInfoState()

    object Loading : UserInfoState()

    class Success(
        val id: String,
        val profilePic: Uri,
        val username: String,
        val joinDate: String,
        val rating: Float
    ) : UserInfoState()

    class Failure(val message: Int = R.string.cannotLoadUserInfo) : UserInfoState()
}

internal fun User.toUserInfoState(stringResources: StringResources): UserInfoState {
    return UserInfoState.Success(
        this.id,
        this.profilePicUri,
        this.username,
        stringResources.getString(R.string.memberSince, formatDate(this.joinDate)),
        this.rating
    )
}
