package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.data.SaveImagesRequest
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ImageRepository
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class SaveItemUseCase(
    private val itemRepository: ItemRepository,
    private val imageRepository: ImageRepository
) {
    suspend fun saveItem(item: Item): Response<ResponseFlag> {
        val createItemRecordResult = itemRepository.createItemRecord(item)
        createItemRecordResult.data?.let { itemId ->
            val saveItemImagesResult = imageRepository.saveItemImages(itemId, item.imagesUri)
            saveItemImagesResult.data?.let { imagesUri ->
                val updateItemImagesResult = itemRepository.updateItemImages(SaveImagesRequest(itemId, imagesUri))
                if (updateItemImagesResult.success) {
                    return Response(true, ResponseFlag.SUCCESS)
                } else {
                    return Response(false, ResponseFlag.FAIL)
                }
            } ?: run {
                return Response(false, ResponseFlag.FAIL)
            }
        } ?: run {
            return Response(false, ResponseFlag.FAIL)
        }
    }
}
