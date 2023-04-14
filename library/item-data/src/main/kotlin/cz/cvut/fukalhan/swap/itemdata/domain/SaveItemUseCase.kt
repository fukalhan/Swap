package cz.cvut.fukalhan.swap.itemdata.domain


import cz.cvut.fukalhan.swap.itemdata.model.Item

class SaveItemUseCase(
    private val itemRepository: ItemRepository,
    private val imageRepository: ImageRepository
) {
    suspend fun saveItem(item: Item): SaveItemResponse {
        val saveItemResponse = itemRepository.saveItem(item)
        return if (saveItemResponse.success) {
            saveItemResponse.data?.let { itemId ->
                val response = imageRepository.saveItemImages(itemId, item.imagesUri)
                response.data?.let {imagesUri ->
                    itemRepository.updateItemImages(itemId, imagesUri)
                    SaveItemResponse.SUCCESS
                } ?: run {
                    SaveItemResponse.FAIL
                }
            } ?: run {
                SaveItemResponse.FAIL
            }
        } else {
            SaveItemResponse.FAIL
        }
    }
}