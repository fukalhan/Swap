package cz.cvut.fukalhan.swap.userdata.domain.repo

import android.net.Uri
import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.model.Notification
import cz.cvut.fukalhan.swap.userdata.model.Review
import cz.cvut.fukalhan.swap.userdata.model.User

interface UserRepository {
    suspend fun getUserProfileData(userId: String): DataResponse<ResponseFlag, User>

    suspend fun getUsersProfilePic(reviews: List<Review>): DataResponse<ResponseFlag, List<Review>>

    suspend fun changeUserProfilePicture(userId: String, profilePic: Uri): Response<ResponseFlag>

    suspend fun updateUserBio(userId: String, bio: String): Response<ResponseFlag>

    suspend fun getNotificationData(userId: String, itemId: String): DataResponse<ResponseFlag, Notification>
}
