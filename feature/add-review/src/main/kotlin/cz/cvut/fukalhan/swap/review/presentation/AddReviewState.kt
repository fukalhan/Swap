package cz.cvut.fukalhan.swap.review.presentation

import cz.cvut.fukalhan.swap.review.R

sealed class AddReviewState {

    object Init : AddReviewState()

    object Loading : AddReviewState()

    data class Success(val message: Int = R.string.addReviewSuccess) : AddReviewState()

    data class Failure(val message: Int = R.string.addReviewFailed) : AddReviewState()
}
