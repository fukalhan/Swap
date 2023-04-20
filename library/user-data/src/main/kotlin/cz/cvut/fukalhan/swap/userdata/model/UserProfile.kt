package cz.cvut.fukalhan.swap.userdata.model

import android.net.Uri

data class UserProfile(
    val id: String,
    val profilePicUri: Uri,
    val username: String,
    val joinDate: String
)
