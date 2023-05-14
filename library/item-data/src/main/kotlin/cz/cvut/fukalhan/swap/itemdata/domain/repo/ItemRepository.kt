package cz.cvut.fukalhan.swap.itemdata.domain.repo

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.SaveImagesRequest
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail
import cz.cvut.fukalhan.swap.itemdata.model.State

interface ItemRepository {

    suspend fun createItemRecord(item: Item): DataResponse<String>

    suspend fun updateItemImages(saveImagesRequest: SaveImagesRequest): Response

    suspend fun getUserItems(uid: String): DataResponse<List<Item>>

    suspend fun getItems(uid: String): DataResponse<List<Item>>

    suspend fun getItemsById(ids: List<String>): DataResponse<List<Item>>

    suspend fun getItemDetail(id: String): DataResponse<ItemDetail>

    suspend fun getItemLikeState(userId: String, itemId: String): DataResponse<Boolean>

    suspend fun likeItem(userId: String, itemId: String): Response

    suspend fun dislikeItem(userId: String, itemId: String): Response

    suspend fun getItemIdsLikedByUser(userId: String): DataResponse<List<String>>

    suspend fun changeItemState(itemId: String, state: State): Response
}
