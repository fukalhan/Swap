package cz.cvut.fukalhan.swap.review.mapper

import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.system.model.UserInfoViewVo
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.design.R
import cz.cvut.fukalhan.swap.review.model.AddReviewScreenData
import cz.cvut.fukalhan.swap.userdata.model.User
import cz.cvut.fukalhan.swap.review.view.AddReviewScreen

/**
 * Map [User] data to [AddReviewScreenData] view object to display on [AddReviewScreen]
 */
internal fun User.toVo() =
    AddReviewScreenData(
        userInfo = UserInfoViewVo(
            username = username,
            profilePicUri = profilePicUri,
            joinDate = StringModel.Resource(id = R.string.memberSince, formatDate(this.joinDate)),
            rating = rating
        )
    )