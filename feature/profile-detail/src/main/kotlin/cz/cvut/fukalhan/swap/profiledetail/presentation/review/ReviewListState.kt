package cz.cvut.fukalhan.swap.profiledetail.presentation.review

import android.net.Uri
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.userdata.model.Review

sealed class ReviewListState {
    object Init : ReviewListState()

    object Loading : ReviewListState()

    data class DataLoaded(
        val reviews: List<ReviewState>
    ) : ReviewListState()

    data class Empty(val message: Int = R.string.noReviewsToDisplay) : ReviewListState()

    data class Failure(val message: Int = R.string.cannotLoadReviews) : ReviewListState()
}

data class ReviewState(
    val id: String,
    val reviewerId: String,
    val reviewerProfilePic: Uri,
    val rating: Int,
    val description: String
)

internal fun Review.toReviewState(): ReviewState {
    return ReviewState(
        this.id,
        this.reviewerId,
        this.reviewerProfilePic,
        this.rating,
        this.description
    )
}
