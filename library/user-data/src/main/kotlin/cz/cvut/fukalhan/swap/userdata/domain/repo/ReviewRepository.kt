package cz.cvut.fukalhan.swap.userdata.domain.repo

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.model.Review

interface ReviewRepository {
    suspend fun addReview(review: Review): Response<ResponseFlag>

    suspend fun getUserRating(userId: String): DataResponse<ResponseFlag, Float>

    suspend fun getUserReviews(userId: String): DataResponse<ResponseFlag, List<Review>>
}
