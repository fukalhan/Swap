package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.SearchQuery

class SearchItemsUseCase(private val itemRepository: ItemRepository) {

    suspend fun getSearchedItems(
        userId: String,
        searchQuery: SearchQuery
    ): DataResponse<ResponseFlag, Pair<List<Item>, List<String>>> {
        val response = itemRepository.getItems(userId)
        val getItemsLikedByUserResponse = itemRepository.getItemIdsLikedByUser(userId)
        return if (response.data != null && getItemsLikedByUserResponse.data != null) {
            val items = response.data
            val itemsFilteredByCategory = if (searchQuery.category != Category.DEFAULT) {
                items.filter { item ->
                    item.category == searchQuery.category
                }
            } else {
                items
            }

            val itemsFilteredBySearchString = if (searchQuery.searchString.isNotEmpty()) {
                itemsFilteredByCategory.filter { item ->
                    searchForString(searchQuery.searchString, item.name, item.description)
                }
            } else {
                itemsFilteredByCategory
            }
            DataResponse(
                true,
                ResponseFlag.SUCCESS,
                Pair(itemsFilteredBySearchString, getItemsLikedByUserResponse.data)
            )
        } else {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    private fun searchForString(searchString: String, name: String, description: String): Boolean {
        return name.contains(searchString, ignoreCase = true) || description.contains(searchString, ignoreCase = true)
    }
}
