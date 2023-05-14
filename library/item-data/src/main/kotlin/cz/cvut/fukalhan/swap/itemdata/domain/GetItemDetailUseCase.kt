package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail

class GetItemDetailUseCase(private val itemRepository: ItemRepository) {
    suspend fun getItemDetail(userId: String, itemId: String): DataResponse<ItemDetail> {
        val itemDetailResponse = itemRepository.getItemDetail(itemId)
        val isItemLikedResponse = itemRepository.getItemLikeState(userId, itemId)
        return if (itemDetailResponse is DataResponse.Success && isItemLikedResponse is DataResponse.Success) {
            val item = itemDetailResponse.data
            item.isLiked = isItemLikedResponse.data
            DataResponse.Success(item)
        } else {
            DataResponse.Error()
        }
    }
}
