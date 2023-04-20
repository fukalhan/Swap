package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository

class ToggleItemLikeUseCase(private val itemRepository: ItemRepository) {
    suspend fun toggleItemLike(userId: String, itemId: String, isLiked: Boolean): Response<ResponseFlag> {
        return if (isLiked) {
            itemRepository.likeItem(userId, itemId)
        } else {
            itemRepository.dislikeItem(userId, itemId)
        }
    }
}
