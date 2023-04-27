package cz.cvut.fukalhan.swap.settings.presentation

import android.net.Uri
import cz.cvut.fukalhan.swap.settings.R
import cz.cvut.fukalhan.swap.userdata.model.User

sealed class SettingsState

object Init : SettingsState()

object Loading : SettingsState()

data class UserDataLoaded(
    val id: String,
    val profilePic: Uri,
    val username: String,
    val bio: String
) : SettingsState()

internal fun User.toUserData(): SettingsState {
    return UserDataLoaded(
        this.id,
        this.profilePicUri,
        this.username,
        this.bio
    )
}

data class Failure(val message: Int = R.string.cannotLoadData) : SettingsState()

data class ProfilePictureChangeSuccess(val message: Int = R.string.profilePictureChangeSuccess) : SettingsState()

data class ProfilePictureChangeFailed(val message: Int = R.string.profilePictureChangeSuccess) : SettingsState()

data class BioChangeSuccess(val message: Int = R.string.bioUpdateSuccess) : SettingsState()

data class BioChangeFailed(val message: Int = R.string.bioUpdateFail) : SettingsState()
