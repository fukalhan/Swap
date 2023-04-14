package cz.cvut.fukalhan.swap.itemdata.domain

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.data.SaveItemResponse
import cz.cvut.fukalhan.swap.itemdata.model.Item

interface ItemRepository {
    suspend fun saveItem(item: Item): Response<String>

    suspend fun updateItemImages(itemId: String, imagesUri: List<Uri>): Response<SaveItemResponse>
}