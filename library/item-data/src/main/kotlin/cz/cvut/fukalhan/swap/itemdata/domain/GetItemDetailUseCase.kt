package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail

class GetItemDetailUseCase(private val itemRepository: ItemRepository) {
    suspend fun getItemDetail(userId: String, itemId: String): DataResponse<ResponseFlag, ItemDetail> {
        val itemDetailResponse = itemRepository.getItemDetail(itemId)
        val isItemLikedResponse = itemRepository.getItemLikeState(userId, itemId)
        return if (itemDetailResponse.data != null && isItemLikedResponse.data != null) {
            val item = itemDetailResponse.data
            item.isLiked = isItemLikedResponse.data
            DataResponse(true, ResponseFlag.SUCCESS, item)
        } else {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }
}
