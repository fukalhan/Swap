package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetItemsUseCase(private val itemRepository: ItemRepository) {
    suspend fun getItems(uid: String): DataResponse<ResponseFlag, List<Item>> {
        return itemRepository.getItems(uid)
    }
}
