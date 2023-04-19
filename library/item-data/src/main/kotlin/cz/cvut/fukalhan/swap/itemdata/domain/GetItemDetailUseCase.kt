package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetItemDetailUseCase(val itemRepository: ItemRepository) {
    suspend fun getItemDetail(id: String): DataResponse<ResponseFlag, Item> {
        return itemRepository.getItemDetail(id)
    }
}
