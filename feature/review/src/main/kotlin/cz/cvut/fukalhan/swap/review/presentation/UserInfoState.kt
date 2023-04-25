package cz.cvut.fukalhan.swap.review.presentation

import android.net.Uri
import cz.cvut.fukalhan.swap.review.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class UserInfoState {
    object Init : UserInfoState()

    object Loading : UserInfoState()

    class Success(
        val id: String,
        val username: String,
        val profilePic: Uri
    ) : UserInfoState()

    class Failure(val message: Int = R.string.cannotLoadUserInfo) : UserInfoState()
}

internal fun User.toUserInfoState(): UserInfoState {
    return UserInfoState.Success(
        this.id,
        this.username,
        this.profilePicUri
    )
}
