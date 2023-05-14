package cz.cvut.fukalhan.swap.itemdata.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
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
import cz.cvut.fukalhan.swap.itemdata.model.ItemDetail
import cz.cvut.fukalhan.swap.itemdata.model.State
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

    override suspend fun createItemRecord(item: Item): DataResponse<String> {
        val adapter: JsonAdapter<Item> = moshi.adapter(Item::class.java)
        val data = adapter.toJson(item)

        return try {
            val response = functions
                .getHttpsCallable(SAVE_ITEM)
                .call(data)
                .await()

            val result = response.data as HashMap<*, *>
            val id = result[DATA] as String?
            id?.let {
                DataResponse.Success(it)
            } ?: run {
                DataResponse.Error()
            }
        } catch (e: FirebaseFunctionsException) {
            Log.e("createItemRecord", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun updateItemImages(saveImagesRequest: SaveImagesRequest): Response {
        val adapter: JsonAdapter<SaveImagesRequest> = moshi.adapter(SaveImagesRequest::class.java)
        val data = adapter.toJson(saveImagesRequest)

        return try {
            functions
                .getHttpsCallable(UPDATE_ITEM)
                .call(data)
                .await()
            Response.Success
        } catch (e: FirebaseFunctionsException) {
            Log.e("updateItemImages", "Exception $e")
            Response.Error
        }
    }

    override suspend fun getUserItems(uid: String): DataResponse<List<Item>> {
        return try {
            val querySnapshot = db.collection(ITEMS).whereEqualTo(OWNER_ID, uid).get().await()
            val items = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.map { doc ->
                    mapDocSnapshotToItem(doc)
                }
            }
            DataResponse.Success(items)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getUserItems", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getItems(uid: String): DataResponse<List<Item>> {
        return try {
            val querySnapshot = db
                .collection(ITEMS)
                .whereNotEqualTo(OWNER_ID, uid)
                .get()
                .await()
            val items = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.map { doc ->
                    mapDocSnapshotToItem(doc)
                }.filter { item ->
                    item.state != State.SWAPPED
                }
            }
            DataResponse.Success(items)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItems", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getItemsById(ids: List<String>): DataResponse<List<Item>> {
        return try {
            if (ids.isEmpty()) {
                return DataResponse.Success(emptyList())
            }
            val querySnapshot = db
                .collection(ITEMS)
                .whereIn(ID, ids)
                .get()
                .await()
            val items = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.map { doc ->
                    mapDocSnapshotToItem(doc)
                }
            }
            DataResponse.Success(items)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItemsById", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getItemDetail(id: String): DataResponse<ItemDetail> {
        return try {
            val docSnapshot = db.collection(ITEMS).document(id).get().await()
            if (docSnapshot.exists()) {
                val imagesList: List<*>? = docSnapshot.get(IMAGES) as? List<*>
                val item = docSnapshot.toObject(ItemDetail::class.java)?.let { itemDetail ->
                    itemDetail.copy(
                        imagesUri = imagesList?.map { Uri.parse(it as String?) } ?: emptyList()
                    )
                }
                item?.let {
                    return DataResponse.Success(it)
                }
            }
            DataResponse.Error()
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItemDetail", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getItemLikeState(userId: String, itemId: String): DataResponse<Boolean> {
        return try {
            val docSnapshot = db.collection(USERS).document(userId).get().await()
            val likedItems = docSnapshot.get(LIKED_ITEMS) as? List<*>
            val itemLike = likedItems != null && likedItems.contains(itemId)
            DataResponse.Success(itemLike)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItemLikeState", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun likeItem(userId: String, itemId: String): Response {
        return try {
            val userRef = db.collection(USERS).document(userId)
            userRef.update(LIKED_ITEMS, FieldValue.arrayUnion(itemId))
            Response.Success
        } catch (e: FirebaseFirestoreException) {
            Log.e("likeItem", "Exception $e")
            Response.Error
        }
    }

    override suspend fun dislikeItem(userId: String, itemId: String): Response {
        return try {
            val userRef = db.collection(USERS).document(userId)
            val docSnapshot = userRef.get().await()
            val likedItems = docSnapshot.get(LIKED_ITEMS) as? List<*>
            if (likedItems != null && itemId in likedItems) {
                userRef.update(LIKED_ITEMS, FieldValue.arrayRemove(itemId)).await()
            }
            Response.Success
        } catch (e: FirebaseFirestoreException) {
            Log.e("dislikeItem", "Exception $e")
            Response.Error
        }
    }

    override suspend fun getItemIdsLikedByUser(userId: String): DataResponse<List<String>> {
        return try {
            val docSnapshot = db.collection(USERS).document(userId).get().await()
            val likedItems = (docSnapshot.get(LIKED_ITEMS) as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()
            DataResponse.Success(likedItems)
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItemIdsLikedByUser", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun changeItemState(itemId: String, state: State): Response {
        return try {
            val itemRef = db.collection(ITEMS).document(itemId)
            itemRef.update(STATE, state).await()
            Response.Success
        } catch (e: FirebaseFirestoreException) {
            Log.e("changeItemState", "Exception $e")
            Response.Error
        }
    }
}

fun mapDocSnapshotToItem(doc: DocumentSnapshot): Item {
    return Item(
        id = doc.id,
        ownerId = doc.getString(OWNER_ID) ?: EMPTY_FIELD,
        name = doc.getString(NAME) ?: EMPTY_FIELD,
        description = doc.getString(DESCRIPTION) ?: EMPTY_FIELD,
        imagesUri = (doc.get(IMAGES) as? List<*>)?.mapNotNull { Uri.parse(it.toString()) } ?: emptyList(),
        category = Category.valueOf(doc.getString(CATEGORY) ?: Category.DEFAULT.name),
        state = State.valueOf(doc.getString(STATE) ?: State.AVAILABLE.name),
    )
}
