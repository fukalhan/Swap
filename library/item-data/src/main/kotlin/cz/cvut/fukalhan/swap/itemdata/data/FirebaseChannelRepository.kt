package cz.cvut.fukalhan.swap.itemdata.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ChannelRepository
import cz.cvut.fukalhan.swap.itemdata.model.Channel
import cz.cvut.fukalhan.swap.itemdata.model.Item
import kotlinx.coroutines.tasks.await

class FirebaseChannelRepository : ChannelRepository {
    private val db = Firebase.firestore
    override suspend fun createChannel(channel: Channel): DataResponse<ResponseFlag, String> {
        return try {
            val docRef = db.collection(CHANNELS).add(channel).await()
            DataResponse(true, ResponseFlag.SUCCESS, docRef.id)
        } catch (e: FirebaseFirestoreException) {
            Log.e("createChannel", "Exception $e")
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun channelExist(channel: Channel): DataResponse<ResponseFlag, String> {
        return try {
            val querySnapshot = db
                .collection(CHANNELS)
                .whereEqualTo(USER_ID, channel.userId)
                .whereEqualTo(ITEM_ID, channel.itemId)
                .whereEqualTo(OWNER_ID, channel.ownerId)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                DataResponse(true, ResponseFlag.SUCCESS, querySnapshot.documents[0].id)
            } else {
                DataResponse(true, ResponseFlag.SUCCESS)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("channelExist", "Exception $e")
            DataResponse(false, ResponseFlag.FAIL)
        }
    }

    override suspend fun getItemFromChannel(channelId: String): DataResponse<ResponseFlag, Item> {
        return try {
            val docSnapshot = db.collection(CHANNELS).document(channelId).get().await()
            if (docSnapshot.exists()) {
                val itemId = (docSnapshot.get(ITEM_ID) ?: EMPTY_FIELD).toString()
                val itemSnapshot = db.collection(ITEMS).document(itemId).get().await()
                DataResponse(true, ResponseFlag.SUCCESS, mapDocSnapshotToItem(itemSnapshot))
            } else {
                DataResponse(false, ResponseFlag.FAIL)
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItemFromChannel", "Exception $e")
            DataResponse(false, ResponseFlag.FAIL)
        }
    }
}
