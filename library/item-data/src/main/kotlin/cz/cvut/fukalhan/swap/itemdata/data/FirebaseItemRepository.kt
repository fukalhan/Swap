package cz.cvut.fukalhan.swap.itemdata.data

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.itemdata.domain.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import kotlinx.coroutines.tasks.await

class FirebaseItemRepository: ItemRepository {
    private val db = Firebase.firestore

    override suspend fun saveItem(item: Item): Response<String> {
        return try {
            val itemRef = db.collection("items").document()
            item.id = itemRef.id

            itemRef.set(item).await()
            Response(true, itemRef.id)
        } catch (e: FirebaseFirestoreException) {
            Response(false)
        }
    }

    override suspend fun updateItemImages(itemId: String, imagesUri: List<Uri>): Response<SaveItemResponse> {
        return try {
            val data = hashMapOf(
                "imagesUri" to imagesUri
            )
            db
                .collection("items")
                .document(itemId)
                .set(data, SetOptions.merge())
                .await()
            Response(true, SaveItemResponse.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(false, SaveItemResponse.FAIL)
        }
    }
}