package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.SearchQuery
import java.text.Normalizer
import java.util.regex.Pattern

class SearchItemsUseCase(private val itemRepository: ItemRepository) {

    suspend fun getSearchedItems(
        userId: String,
        searchQuery: SearchQuery
    ): DataResponse<Pair<List<Item>, List<String>>> {
        val response = itemRepository.getItems(userId)
        val getItemsLikedByUserResponse = itemRepository.getItemIdsLikedByUser(userId)
        return if (response is DataResponse.Success && getItemsLikedByUserResponse is DataResponse.Success) {
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
            DataResponse.Success(Pair(itemsFilteredBySearchString, getItemsLikedByUserResponse.data))
        } else {
            DataResponse.Error()
        }
    }

    // Search string function with removed diacritics
    private fun searchForString(searchString: String, name: String, description: String): Boolean {
        val normalizedSearchString = Normalizer
            .normalize(searchString, Normalizer.Form.NFD)
            .replace("\\p{M}".toRegex(), "")

        val pattern = Pattern.compile(normalizedSearchString, Pattern.CASE_INSENSITIVE)

        val normalizedName = Normalizer
            .normalize(name, Normalizer.Form.NFD)
            .replace("\\p{M}".toRegex(), "")
        val normalizedDescription = Normalizer
            .normalize(description, Normalizer.Form.NFD)
            .replace("\\p{M}".toRegex(), "")

        return pattern.matcher(normalizedName).find() || pattern.matcher(normalizedDescription).find()
    }
}
