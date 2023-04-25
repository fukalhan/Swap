package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.User
import kotlin.math.roundToInt

class GetUserProfileDataUseCase(private val repository: UserRepository) {
    suspend fun getUserProfileData(userId: String): DataResponse<ResponseFlag, User> {
        val userResponse = repository.getUserProfileData(userId)
        val ratingResponse = repository.getUserRating(userId)
        return if (userResponse.data != null && ratingResponse.data != null) {
            val user = userResponse.data
            user.rating = ((ratingResponse.data * 10).roundToInt() / 10).toFloat()
            DataResponse(ResponseFlag.SUCCESS, user)
        } else {
            DataResponse(ResponseFlag.FAIL)
        }
    }
}
