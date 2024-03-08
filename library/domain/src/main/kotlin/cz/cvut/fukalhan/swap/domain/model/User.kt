package cz.cvut.fukalhan.swap.domain.model

import android.net.Uri

/**
 * Model for a user
 *
 * @param id unique user identifier
 * @param username user's display name
 * @param profileImage uri of user's profile picture
 * @param joinDate date when the user registered to the platform
 * @param rating rating based on user's reviews from other users
 * @param bio user's profile description
 * @param fcmToken
 */
data class User(
    var id: String = "",
    var username: String = "",
    var profileImage: Uri = Uri.EMPTY,
    var joinDate: String = "",
    var rating: Float = 0f,
    var bio: String = "",
    var fcmToken: String = ""
)
