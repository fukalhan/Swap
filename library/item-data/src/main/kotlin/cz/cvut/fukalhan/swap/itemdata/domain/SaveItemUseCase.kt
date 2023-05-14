package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.SaveImagesRequest
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ImageRepository
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class SaveItemUseCase(
    private val itemRepository: ItemRepository,
    private val imageRepository: ImageRepository
) {
    suspend fun saveItem(item: Item): Response {
        val createItemRecordResult = itemRepository.createItemRecord(item)
        return if (createItemRecordResult is DataResponse.Success) {
            val itemId = createItemRecordResult.data
            val saveItemImagesResult = imageRepository.saveItemImages(createItemRecordResult.data, item.imagesUri)
            if (saveItemImagesResult is DataResponse.Success) {
                val imagesUri = saveItemImagesResult.data
                itemRepository.updateItemImages(SaveImagesRequest(itemId, imagesUri))
            } else {
                Response.Error
            }
        } else {
            Response.Error
        }
    }
}
