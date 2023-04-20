package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetUserLikedItemsUseCase(private val itemRepository: ItemRepository) {
    suspend fun getUserLikedItems(uid: String): DataResponse<ResponseFlag, List<Item>> {
        itemRepository.getItemIdsLikedByUser(uid).data?.let { ids ->
            return itemRepository.getItemsById(ids)
        } ?: run {
            return DataResponse(false, ResponseFlag.FAIL)
        }
    }
}
