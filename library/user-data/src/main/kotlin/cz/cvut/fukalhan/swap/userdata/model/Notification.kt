package cz.cvut.fukalhan.swap.userdata.model

import android.net.Uri

data class Notification(
    val userId: String,
    val userProfilePic: Uri,
    val username: String,
    val itemId: String,
    val itemName: String
)
