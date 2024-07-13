package cz.cvut.fukalhan.swap.review.model

import cz.cvut.fukalhan.design.system.model.UserInfoViewVo
import cz.cvut.fukalhan.swap.review.view.AddReviewScreen

/**
 * View object for [AddReviewScreen]
 *
 * @property userInfo information of the reviewed user
 * @property rating current rating in the range 0 to 5
 * @property review review word description
 */
data class AddReviewScreenData(
    val userInfo: UserInfoViewVo? = null,
    val rating: Int = 0,
    val review: String = ""
) {
    val allFieldsFilled = rating != 0 && review.isNotBlank()

    companion object {
        const val REVIEW_CHAR_LIMIT = 150
        const val MIN_RATING = 1
        const val MAX_RATING = 5
    }
}