package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.Review

class GetUserReviewsUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend fun getReviews(userId: String): DataResponse<ResponseFlag, List<Review>> {
        val reviewsResponse = reviewRepository.getUserReviews(userId)
        return if (reviewsResponse.data != null) {
            val reviewersProfilePicResponse = userRepository.getUsersProfilePic(reviewsResponse.data)
            reviewersProfilePicResponse.data?.let { reviews ->
                DataResponse(ResponseFlag.SUCCESS, reviews)
            } ?: run {
                DataResponse(ResponseFlag.FAIL)
            }
        } else {
            DataResponse(ResponseFlag.FAIL)
        }
    }
}
