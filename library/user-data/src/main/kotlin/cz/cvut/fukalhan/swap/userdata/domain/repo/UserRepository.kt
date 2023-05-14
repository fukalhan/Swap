package cz.cvut.fukalhan.swap.userdata.domain.repo

import android.net.Uri
import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.model.Notification
import cz.cvut.fukalhan.swap.userdata.model.Review
import cz.cvut.fukalhan.swap.userdata.model.User

interface UserRepository {
    suspend fun getUserProfileData(userId: String): DataResponse<User>

    suspend fun getUsersProfilePic(reviews: List<Review>): DataResponse<List<Review>>

    suspend fun changeUserProfilePicture(userId: String, profilePic: Uri): Response

    suspend fun updateUserBio(userId: String, bio: String): Response

    suspend fun getNotificationData(userId: String, itemId: String): DataResponse<Notification>

    suspend fun getUsersById(userIds: List<String>): DataResponse<List<User>>
}
