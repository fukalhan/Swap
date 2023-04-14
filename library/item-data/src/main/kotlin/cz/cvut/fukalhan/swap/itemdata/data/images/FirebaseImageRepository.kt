package cz.cvut.fukalhan.swap.itemdata.data.images

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import cz.cvut.fukalhan.swap.itemdata.data.Response
import cz.cvut.fukalhan.swap.itemdata.domain.ImageRepository
import kotlinx.coroutines.tasks.await

class FirebaseImageRepository: ImageRepository {
    private val storage = Firebase.storage

    override suspend fun saveItemImages(itemId: String, imagesUri: List<Uri>): Response<List<Uri>> {
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
        return Response(true, downloadUris)
    }
}