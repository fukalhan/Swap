package cz.cvut.fukalhan.swap.itemdetail.mapper

import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail
import cz.cvut.fukalhan.swap.itemdetail.R
import cz.cvut.fukalhan.swap.itemdetail.model.ItemDetailScreenData
import cz.cvut.fukalhan.swap.itemdetail.model.OwnerInfoVo
import cz.cvut.fukalhan.swap.userdata.model.User

/**
 * Mapping of [ItemDetail] to [ItemDetailScreenData] with the owner info
 */
internal fun ItemDetail.toItemDetailScreenData(
    owner: User
) : ItemDetailScreenData {
    return ItemDetailScreenData(
        id = id,
        name = name,
        description = description,
        images = imagesUri,
        category = category,
        state = state,
        isLiked = isLiked,
        ownerInfoVo = owner.toOwnerInfoVo()
    )
}


/**
 * Mapping of domain [User] to [OwnerInfoVo]
 */
private fun User.toOwnerInfoVo(): OwnerInfoVo =
    OwnerInfoVo(
        id = id,
        profilePic = profilePicUri,
        username = username,
        joinDate = StringModel.Resource(id = R.string.join_date_info, params = arrayOf(formatDate(this.joinDate))),
        rating = rating
    )