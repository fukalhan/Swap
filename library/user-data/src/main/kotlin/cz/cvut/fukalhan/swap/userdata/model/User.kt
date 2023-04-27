package cz.cvut.fukalhan.swap.userdata.model

import android.net.Uri

data class User(
    var id: String = "",
    var profilePicUri: Uri = Uri.EMPTY,
    var username: String = "",
    var joinDate: String = "",
    var rating: Float = 0f,
    var bio: String = ""
)
