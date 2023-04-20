package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetItemsUseCase(private val itemRepository: ItemRepository) {
    suspend fun getItems(uid: String): DataResponse<ResponseFlag, Pair<List<Item>, List<String>>> {
        val getItemsResponse = itemRepository.getItems(uid)
        val getItemsLikedByUserResponse = itemRepository.getItemIdsLikedByUser(uid)
        return if (getItemsResponse.data != null && getItemsLikedByUserResponse.data != null) {
            DataResponse(
                true,
                ResponseFlag.SUCCESS,
                Pair(getItemsResponse.data, getItemsLikedByUserResponse.data)
            )
        } else {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }
}
