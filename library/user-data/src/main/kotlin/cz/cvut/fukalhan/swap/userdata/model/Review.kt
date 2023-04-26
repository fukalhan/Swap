package cz.cvut.fukalhan.swap.userdata.model

import android.net.Uri

data class Review(
    var id: String = "",
    var userId: String = "",
    var reviewerId: String = "",
    var reviewerProfilePic: Uri = Uri.EMPTY,
    var rating: Int = 0,
    var description: String = ""
)
