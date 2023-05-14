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
    override suspend fun createChannel(channel: Channel): DataResponse<String> {
        return try {
            val docRef = db.collection(CHANNELS).add(channel).await()
            DataResponse.Success(docRef.id)
        } catch (e: FirebaseFirestoreException) {
            Log.e("createChannel", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun channelExist(channel: Channel): DataResponse<String> {
        return try {
            val querySnapshot = db
                .collection(CHANNELS)
                .whereEqualTo(USER_ID, channel.userId)
                .whereEqualTo(ITEM_ID, channel.itemId)
                .whereEqualTo(OWNER_ID, channel.ownerId)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                DataResponse.Success(querySnapshot.documents[0].id)
            } else {
                DataResponse.Error()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("channelExist", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getItemFromChannel(channelId: String): DataResponse<Item> {
        return try {
            val docSnapshot = db.collection(CHANNELS).document(channelId).get().await()
            if (docSnapshot.exists()) {
                val itemId = (docSnapshot.get(ITEM_ID) ?: EMPTY_FIELD).toString()
                val itemSnapshot = db.collection(ITEMS).document(itemId).get().await()
                DataResponse.Success(mapDocSnapshotToItem(itemSnapshot))
            } else {
                DataResponse.Error()
            }
        } catch (e: FirebaseFirestoreException) {
            Log.e("getItemFromChannel", "Exception $e")
            DataResponse.Error()
        }
    }
}
