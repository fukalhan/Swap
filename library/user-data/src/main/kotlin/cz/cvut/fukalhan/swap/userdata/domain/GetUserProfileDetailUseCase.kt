package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.UserDetail

class GetUserProfileDetailUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend fun getUserProfileDetail(userId: String): DataResponse<ResponseFlag, UserDetail> {
        val userResponse = userRepository.getUserProfileData(userId)
        val reviewsResponse = reviewRepository.getUserReviews(userId)
        val ratingResponse = reviewRepository.getUserRating(userId)

        return if (userResponse.data != null && reviewsResponse.data != null && ratingResponse.data != null) {
            val reviewersProfilePicResponse = userRepository.getUsersProfilePic(reviewsResponse.data)
            reviewersProfilePicResponse.data?.let { reviews ->
                val user = userResponse.data
                user.rating = ratingResponse.data * 10 / 10
                DataResponse(ResponseFlag.SUCCESS, UserDetail(user, reviews))
            } ?: run {
                DataResponse(ResponseFlag.FAIL)
            }
        } else {
            DataResponse(ResponseFlag.FAIL)
        }
    }
}
