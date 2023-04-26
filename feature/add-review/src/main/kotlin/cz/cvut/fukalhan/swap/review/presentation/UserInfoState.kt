package cz.cvut.fukalhan.swap.review.presentation

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.review.R
import cz.cvut.fukalhan.swap.userdata.model.User
import java.text.SimpleDateFormat
import java.util.Locale

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
