package cz.cvut.fukalhan.swap.profile.presentation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import cz.cvut.fukalhan.swap.profile.R
import cz.cvut.fukalhan.swap.userdata.model.UserProfile
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileState(
    val profilePicUri: Uri? = null,
    val username: String = "Uživatelské jméno",
    val joinDate: @Composable () -> String = { "Datum založení účtu" }
)

fun UserProfile.toProfileState(): ProfileState {
    return ProfileState(
        this.profilePicUri,
        this.username
    ) { stringResource(R.string.memberSince, convertDateString(this.joinDate)) }
}

private fun convertDateString(dateString: String): String {
    val inputDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
    val outputDateFormat = SimpleDateFormat("d.M.yyyy", Locale.US)

    val date = inputDateFormat.parse(dateString)
    return outputDateFormat.format(date)
}
