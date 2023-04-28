package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.User

class GetUserDataUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {
    suspend fun getUserData(userId: String): DataResponse<ResponseFlag, User> {
        val userResponse = userRepository.getUserProfileData(userId)
        val ratingResponse = reviewRepository.getUserRating(userId)
        return if (userResponse.data != null && ratingResponse.data != null) {
            val user = userResponse.data
            user.rating = ratingResponse.data * 10 / 10
            DataResponse(ResponseFlag.SUCCESS, user)
        } else {
            DataResponse(ResponseFlag.FAIL)
        }
    }
}
