package cz.cvut.fukalhan.swap.itemdata.domain.repo

import android.net.Uri
import cz.cvut.fukalhan.swap.itemdata.data.DataResponse

interface ImageRepository {
    suspend fun saveItemImages(itemId: String, imagesUri: List<Uri>): DataResponse<List<Uri>>
}
