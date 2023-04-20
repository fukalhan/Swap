package cz.cvut.fukalhan.swap.itemdata.data

import android.net.Uri
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctionsException
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.model.Category
import cz.cvut.fukalhan.swap.itemdata.model.Item
import cz.cvut.fukalhan.swap.itemdata.model.ItemState
import cz.cvut.fukalhan.swap.itemdata.tools.UriAdapter
import kotlinx.coroutines.tasks.await

class FirebaseItemRepository : ItemRepository {
    private val db = Firebase.firestore
    private val functions = Firebase.functions
    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .add(UriAdapter)
        .build()

    override suspend fun createItemRecord(item: Item): DataResponse<ResponseFlag, String> {
        val adapter: JsonAdapter<Item> = moshi.adapter(Item::class.java)
        val data = adapter.toJson(item)

        return try {
            val response = functions
                .getHttpsCallable("saveItem")
                .call(data)
                .await()

            val result = response.data as HashMap<*, *>
            val success = result["success"] as Boolean
            val flag = result["flag"] as Int
            val id = result["data"] as String?
            DataResponse(success, mapResponseFlag(flag), id)
        } catch (e: FirebaseFunctionsException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun updateItemImages(saveImagesRequest: SaveImagesRequest): Response<ResponseFlag> {
        val adapter: JsonAdapter<SaveImagesRequest> = moshi.adapter(SaveImagesRequest::class.java)
        val data = adapter.toJson(saveImagesRequest)

        return try {
            val response = functions
                .getHttpsCallable("updateItem")
                .call(data)
                .await()

            val result = response.data as HashMap<*, *>
            val success = result["success"] as Boolean
            val flag = result["flag"] as Int
            Response(success, mapResponseFlag(flag))
        } catch (e: FirebaseFunctionsException) {
            Response(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getUsersItems(uid: String): DataResponse<ResponseFlag, List<Item>> {
        return try {
            val querySnapshot = db.collection("items").whereEqualTo("ownerId", uid).get().await()
            val items = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.map { doc ->
                    Item(
                        id = doc.id,
                        ownerId = doc.getString("ownerId") ?: "",
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        imagesUri = (doc.get("images") as? List<String>)?.map { Uri.parse(it) } ?: emptyList(),
                        category = Category.valueOf(doc.getString("category") ?: Category.DEFAULT.name),
                        state = ItemState.valueOf(doc.getString("state") ?: ItemState.AVAILABLE.name),
                    )
                }
            }
            DataResponse(true, ResponseFlag.SUCCESS, items)
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItems(uid: String): DataResponse<ResponseFlag, List<Item>> {
        return try {
            val querySnapshot = db.collection("items").whereNotEqualTo("ownerId", uid).get().await()
            val items = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.map { doc ->
                    Item(
                        id = doc.id,
                        ownerId = doc.getString("ownerId") ?: "",
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        imagesUri = (doc.get("images") as? List<String>)?.map { Uri.parse(it) } ?: emptyList(),
                        category = Category.valueOf(doc.getString("category") ?: Category.DEFAULT.name),
                        state = ItemState.valueOf(doc.getString("state") ?: ItemState.AVAILABLE.name),
                    )
                }
            }
            DataResponse(true, ResponseFlag.SUCCESS, items)
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItemDetail(id: String): DataResponse<ResponseFlag, Item> {
        return try {
            val docSnapshot = db.collection("items").document(id).get().await()
            if (docSnapshot.exists()) {
                val imagesList: List<*>? = docSnapshot.get("images") as? List<*>
                val item = docSnapshot.toObject(Item::class.java)?.let { it ->
                    it.copy(
                        imagesUri = imagesList?.map { Uri.parse(it as String?) } ?: emptyList()
                    )
                }
                DataResponse(
                    true,
                    ResponseFlag.SUCCESS,
                    item
                )
            } else {
                DataResponse(false, ResponseFlag.FAIL)
            }
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun likeItem(userId: String, itemId: String): Response<ResponseFlag> {
        return try {
            val userRef = db.collection("Users").document(userId)
            userRef.update("likedItems", FieldValue.arrayUnion(itemId))
            Response(true, ResponseFlag.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun dislikeItem(userId: String, itemId: String): Response<ResponseFlag> {
        return try {
            val userRef = db.collection("Users").document(userId)
            val docSnapshot = userRef.get().await()
            val likedItems = docSnapshot.get("likedItems") as? List<String>
            if (likedItems != null && itemId in likedItems) {
                userRef.update("likedItems", FieldValue.arrayRemove(itemId)).await()
            }
            Response(true, ResponseFlag.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(false, ResponseFlag.FAIL)
        }
    }
}
