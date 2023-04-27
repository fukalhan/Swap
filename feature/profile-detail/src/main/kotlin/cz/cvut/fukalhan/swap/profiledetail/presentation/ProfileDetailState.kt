package cz.cvut.fukalhan.swap.profiledetail.presentation

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.profiledetail.R
import cz.cvut.fukalhan.swap.userdata.model.Review
import cz.cvut.fukalhan.swap.userdata.model.User
import cz.cvut.fukalhan.swap.userdata.model.UserDetail
import java.text.SimpleDateFormat
import java.util.Locale

sealed class ProfileDetailState

object Init : ProfileDetailState()

object Loading : ProfileDetailState()

data class Success(
    val user: UserState,
    val reviews: List<ReviewState>
) : ProfileDetailState()

data class UserState(
    val id: String,
    val profilePic: Uri,
    val username: String,
    val joinDate: String,
    val rating: Float,
    val bio: String
)

private fun User.toUserState(stringResources: StringResources): UserState {
    return UserState(
        this.id,
        this.profilePicUri,
        this.username,
        stringResources.getString(R.string.memberSince, convertDateString(this.joinDate)),
        this.rating,
        this.bio
    )
}

data class ReviewState(
    val id: String,
    val reviewerId: String,
    val reviewerProfilePic: Uri,
    val rating: Int,
    val description: String
)

private fun Review.toReviewState(): ReviewState {
    return ReviewState(
        this.id,
        this.reviewerId,
        this.reviewerProfilePic,
        this.rating,
        this.description
    )
}

internal fun UserDetail.toProfileDetailState(stringResources: StringResources): ProfileDetailState {
    return Success(
        this.user.toUserState(stringResources),
        this.reviews.map {
            it.toReviewState()
        }
    )
}

data class Failure(val message: Int = R.string.cannotLoadUserData) : ProfileDetailState()

private fun convertDateString(dateString: String): String {
    val inputDateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
    val outputDateFormat = SimpleDateFormat("d.M.yyyy", Locale.US)

    val date = inputDateFormat.parse(dateString)
    return outputDateFormat.format(date)
}
