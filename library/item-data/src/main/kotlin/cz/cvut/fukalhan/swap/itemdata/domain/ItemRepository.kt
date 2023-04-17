package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.data.SaveImagesRequest
import cz.cvut.fukalhan.swap.itemdata.model.Item

interface ItemRepository {
    suspend fun createItemRecord(item: Item): DataResponse<ResponseFlag, String>

    suspend fun updateItemImages(saveImagesRequest: SaveImagesRequest): Response<ResponseFlag>
}
