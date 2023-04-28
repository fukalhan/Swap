package cz.cvut.fukalhan.swap.profiledetail.presentation.user

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.userdata.model.User
import java.text.SimpleDateFormat
import java.util.Locale

sealed class UserInfoState {
    object Init : UserInfoState()

    object Loading : UserInfoState()

    data class DataLoaded(
        val user: UserState
    ) : UserInfoState()

    data class Failure(val message: Int = R.string.cannotLoadUserData) : UserInfoState()
}

data class UserState(
    val id: String,
    val profilePic: Uri,
    val username: String,
    val joinDate: String,
    val rating: Float,
    val bio: String
)

internal fun User.toUserState(stringResources: StringResources): UserInfoState {
    return UserInfoState.DataLoaded(
        UserState(
            this.id,
            this.profilePicUri,
            this.username,
            stringResources.getString(R.string.memberSince, convertDateString(this.joinDate)),
            this.rating,
            this.bio
        )
    )
}

private fun convertDateString(dateString: String): String {
    val inputDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
    val outputDateFormat = SimpleDateFormat("d.M.yyyy", Locale.US)

    val date = inputDateFormat.parse(dateString)
    return outputDateFormat.format(date)
}
