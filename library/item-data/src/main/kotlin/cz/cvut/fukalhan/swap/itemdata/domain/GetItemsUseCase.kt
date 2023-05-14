package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetItemsUseCase(private val itemRepository: ItemRepository) {
    suspend fun getItems(uid: String): DataResponse<Pair<List<Item>, List<String>>> {
        val getItemsResponse = itemRepository.getItems(uid)
        val getItemsLikedByUserResponse = itemRepository.getItemIdsLikedByUser(uid)
        return if (getItemsResponse is DataResponse.Success && getItemsLikedByUserResponse is DataResponse.Success) {
            DataResponse.Success(Pair(getItemsResponse.data, getItemsLikedByUserResponse.data))
        } else {
            DataResponse.Error()
        }
    }
}
