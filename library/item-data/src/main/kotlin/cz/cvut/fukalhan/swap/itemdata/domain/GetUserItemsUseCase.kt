package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetUserItemsUseCase(private val itemRepository: ItemRepository) {

    suspend fun getUserItems(uid: String): DataResponse<ResponseFlag, List<Item>> = itemRepository.getUsersItems(uid)
}
