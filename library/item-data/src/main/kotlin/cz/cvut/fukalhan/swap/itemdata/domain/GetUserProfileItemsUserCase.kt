package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetUserProfileItemsUserCase(private val itemRepository: ItemRepository) {

    suspend fun getItems(userId: String, ownerId: String): DataResponse<ResponseFlag, Pair<List<Item>, List<String>>> {
        val userItemsResponse = itemRepository.getUserItems(ownerId)
        val getItemsLikedByUserResponse = itemRepository.getItemIdsLikedByUser(userId)
        return if (userItemsResponse.data != null && getItemsLikedByUserResponse.data != null) {
            DataResponse(
                true,
                ResponseFlag.SUCCESS,
                Pair(userItemsResponse.data, getItemsLikedByUserResponse.data)
            )
        } else {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }
}
