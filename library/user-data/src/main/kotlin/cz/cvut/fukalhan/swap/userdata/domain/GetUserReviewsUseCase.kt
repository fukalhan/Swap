package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.Review

class GetUserReviewsUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend fun getReviews(userId: String): DataResponse<List<Review>> {
        val reviewsResponse = reviewRepository.getUserReviews(userId)
        return if (reviewsResponse is DataResponse.Success) {
            val reviewersProfilePicResponse = userRepository.getUsersProfilePic(reviewsResponse.data)
            if (reviewersProfilePicResponse is DataResponse.Success) {
                DataResponse.Success(reviewersProfilePicResponse.data)
            } else {
                DataResponse.Error()
            }
        } else {
            DataResponse.Error()
        }
    }
}
