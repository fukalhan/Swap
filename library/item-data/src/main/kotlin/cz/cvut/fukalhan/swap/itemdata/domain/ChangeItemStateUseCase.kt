package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.State

class ChangeItemStateUseCase(private val itemRepository: ItemRepository) {
    suspend fun changeItemState(itemId: String, state: State): Response {
        return itemRepository.changeItemState(itemId, state)
    }
}
