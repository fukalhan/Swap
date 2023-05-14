package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetUserProfileItemsUserCase(private val itemRepository: ItemRepository) {

    suspend fun getItems(userId: String, ownerId: String): DataResponse<Pair<List<Item>, List<String>>> {
        val userItemsResponse = itemRepository.getUserItems(ownerId)
        val getItemsLikedByUserResponse = itemRepository.getItemIdsLikedByUser(userId)
        return if (userItemsResponse is DataResponse.Success && getItemsLikedByUserResponse is DataResponse.Success) {
            DataResponse.Success(Pair(userItemsResponse.data, getItemsLikedByUserResponse.data))
        } else {
            DataResponse.Error()
        }
    }
}
