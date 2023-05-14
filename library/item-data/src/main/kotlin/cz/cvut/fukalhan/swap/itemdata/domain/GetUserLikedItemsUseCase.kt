package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetUserLikedItemsUseCase(private val itemRepository: ItemRepository) {
    suspend fun getUserLikedItems(uid: String): DataResponse<List<Item>> {
        val response = itemRepository.getItemIdsLikedByUser(uid)
        return when (response) {
            is DataResponse.Success -> itemRepository.getItemsById(response.data)
            is DataResponse.Error -> DataResponse.Error()
        }
    }
}
