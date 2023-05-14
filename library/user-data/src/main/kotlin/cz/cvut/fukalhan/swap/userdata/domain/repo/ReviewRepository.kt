package cz.cvut.fukalhan.swap.userdata.domain.repo

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.model.Review

interface ReviewRepository {
    suspend fun addReview(review: Review): Response

    suspend fun getUserRating(userId: String): DataResponse<Float>

    suspend fun getUserReviews(userId: String): DataResponse<List<Review>>
}
