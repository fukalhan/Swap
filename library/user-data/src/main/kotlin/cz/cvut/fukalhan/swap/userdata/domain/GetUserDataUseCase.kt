package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.User

class GetUserDataUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {
    suspend fun getUserData(userId: String): DataResponse<User> {
        val userResponse = userRepository.getUserProfileData(userId)
        val ratingResponse = reviewRepository.getUserRating(userId)
        return if (userResponse is DataResponse.Success && ratingResponse is DataResponse.Success) {
            val user = userResponse.data
            user.rating = ratingResponse.data * 10 / 10
            DataResponse.Success(user)
        } else {
            DataResponse.Error()
        }
    }
}
