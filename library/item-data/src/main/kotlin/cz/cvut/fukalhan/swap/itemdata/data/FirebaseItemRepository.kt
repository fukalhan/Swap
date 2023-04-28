package cz.cvut.fukalhan.swap.itemdata.data

import android.net.Uri
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
import cz.cvut.fukalhan.swap.itemdata.model.SearchQuery
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

    override suspend fun createItemRecord(item: Item): DataResponse<ResponseFlag, String> {
        val adapter: JsonAdapter<Item> = moshi.adapter(Item::class.java)
        val data = adapter.toJson(item)

        return try {
            val response = functions
                .getHttpsCallable(SAVE_ITEM)
                .call(data)
                .await()

            val result = response.data as HashMap<*, *>
            val success = result[SUCCESS] as Boolean
            val flag = result[FLAG] as Int
            val id = result[DATA] as String?
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
                .getHttpsCallable(UPDATE_ITEM)
                .call(data)
                .await()

            val result = response.data as HashMap<*, *>
            val success = result[SUCCESS] as Boolean
            val flag = result[FLAG] as Int
            Response(success, mapResponseFlag(flag))
        } catch (e: FirebaseFunctionsException) {
            Response(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getUserItems(uid: String): DataResponse<ResponseFlag, List<Item>> {
        return try {
            val querySnapshot = db.collection(ITEMS).whereEqualTo(OWNER_ID, uid).get().await()
            val items = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.map { doc ->
                    mapDocSnapshotToItem(doc)
                }
            }
            DataResponse(true, ResponseFlag.SUCCESS, items)
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItems(uid: String): DataResponse<ResponseFlag, List<Item>> {
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
            DataResponse(true, ResponseFlag.SUCCESS, items)
        } catch (e: Exception) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItemsById(ids: List<String>): DataResponse<ResponseFlag, List<Item>> {
        return try {
            if (ids.isEmpty()) {
                return DataResponse(true, ResponseFlag.SUCCESS, emptyList())
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
            DataResponse(true, ResponseFlag.SUCCESS, items)
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItemDetail(id: String): DataResponse<ResponseFlag, ItemDetail> {
        return try {
            val docSnapshot = db.collection(ITEMS).document(id).get().await()
            if (docSnapshot.exists()) {
                val imagesList: List<*>? = docSnapshot.get(IMAGES) as? List<*>
                val item = docSnapshot.toObject(ItemDetail::class.java)?.let { itemDetail ->
                    itemDetail.copy(
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

    override suspend fun getItemLikeState(
        userId: String,
        itemId: String
    ): DataResponse<ResponseFlag, Boolean> {
        return try {
            val docSnapshot = db.collection(USERS).document(userId).get().await()
            val likedItems = docSnapshot.get(LIKED_ITEMS) as? List<*>
            if (likedItems != null && likedItems.contains(itemId)) {
                return DataResponse(true, ResponseFlag.SUCCESS, true)
            } else {
                DataResponse(true, ResponseFlag.SUCCESS, false)
            }
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun likeItem(userId: String, itemId: String): Response<ResponseFlag> {
        return try {
            val userRef = db.collection(USERS).document(userId)
            userRef.update(LIKED_ITEMS, FieldValue.arrayUnion(itemId))
            Response(true, ResponseFlag.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun dislikeItem(userId: String, itemId: String): Response<ResponseFlag> {
        return try {
            val userRef = db.collection(USERS).document(userId)
            val docSnapshot = userRef.get().await()
            val likedItems = docSnapshot.get(LIKED_ITEMS) as? List<*>
            if (likedItems != null && itemId in likedItems) {
                userRef.update(LIKED_ITEMS, FieldValue.arrayRemove(itemId)).await()
            }
            Response(true, ResponseFlag.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItemIdsLikedByUser(userId: String): DataResponse<ResponseFlag, List<String>> {
        return try {
            val docSnapshot = db.collection(USERS).document(userId).get().await()
            val likedItems = (docSnapshot.get(LIKED_ITEMS) as? List<*>)?.mapNotNull { it?.toString() } ?: emptyList()
            DataResponse(true, ResponseFlag.SUCCESS, likedItems)
        } catch (e: FirebaseFirestoreException) {
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun changeItemState(itemId: String, state: State): Response<ResponseFlag> {
        return try {
            val itemRef = db.collection(ITEMS).document(itemId)
            itemRef.update(STATE, state).await()
            Response(true, ResponseFlag.SUCCESS)
        } catch (e: FirebaseFirestoreException) {
            Response(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getSearchedItems(searchQuery: SearchQuery): DataResponse<ResponseFlag, List<Item>> {
        return try {
            DataResponse(true, ResponseFlag.SUCCESS, emptyList())
        } catch (e: Exception) {
            DataResponse(false, ResponseFlag.FAIL)
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
