package cz.cvut.fukalhan.swap.itemdata.domain

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.data.Response

interface ImageRepository {
    suspend fun saveItemImages(itemId: String, imagesUri: List<Uri>): Response<List<Uri>>
}