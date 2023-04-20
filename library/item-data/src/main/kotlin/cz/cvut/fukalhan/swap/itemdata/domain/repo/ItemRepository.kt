package cz.cvut.fukalhan.swap.itemdata.domain.repo

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.data.SaveImagesRequest
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail

interface ItemRepository {
    suspend fun createItemRecord(item: Item): DataResponse<ResponseFlag, String>
    suspend fun updateItemImages(saveImagesRequest: SaveImagesRequest): Response<ResponseFlag>
    suspend fun getUsersItems(uid: String): DataResponse<ResponseFlag, List<Item>>
    suspend fun getItems(uid: String): DataResponse<ResponseFlag, List<Item>>
    suspend fun getItemDetail(id: String): DataResponse<ResponseFlag, ItemDetail>
    suspend fun getItemLikeState(userId: String, itemId: String): DataResponse<ResponseFlag, Boolean>
    suspend fun likeItem(userId: String, itemId: String): Response<ResponseFlag>
    suspend fun dislikeItem(userId: String, itemId: String): Response<ResponseFlag>
    suspend fun getItemsLikedByUser(userId: String): DataResponse<ResponseFlag, List<String>>
}
