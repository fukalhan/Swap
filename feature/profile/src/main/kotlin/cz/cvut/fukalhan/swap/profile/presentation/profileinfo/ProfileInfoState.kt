package cz.cvut.fukalhan.swap.profile.presentation.profileinfo

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.userdata.model.User
import java.text.SimpleDateFormat
import java.util.Locale

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
