package cz.cvut.fukalhan.swap.itemdata.data

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ImageRepository
import kotlinx.coroutines.tasks.await

class FirebaseImageRepository : ImageRepository {
    private val storage = Firebase.storage

    override suspend fun saveItemImages(itemId: String, imagesUri: List<Uri>): DataResponse<List<Uri>> {
        val storageRef = storage.reference

        val downloadUris = imagesUri.map { uri ->
            val fileName = uri.lastPathSegment ?: throw IllegalArgumentException("Invalid URI")
            val fileRef = storageRef.child("$itemId/$fileName")
            fileRef.putFile(uri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: RuntimeException("Unknown error")
                }
                fileRef.downloadUrl
            }.await()
        }
        return DataResponse.Success(downloadUris)
    }
}
