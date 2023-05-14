package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.model.Review

class AddReviewUseCase(private val reviewRepository: ReviewRepository) {

    suspend fun addReview(review: Review): Response {
        return reviewRepository.addReview(review)
    }
}
