package cz.cvut.fukalhan.swap.domain.model

import android.net.Uri
import androidx.annotation.IntRange

/**
 * Model for user review
 *
 * @param id unique identifier
 * @param userId identifier of the user who is being reviewed
 * @param reviewerId identifier of the reviewer
 * @param reviewerProfilePic
 * @param rating rating
 */
data class Review(
    var id: String = "",
    var userId: String = "",
    var reviewerId: String = "",
    var reviewerProfilePic: Uri = Uri.EMPTY,
    @IntRange(from = 0, to = 5)
    var rating: Int = 0,
    var description: String = ""
)
