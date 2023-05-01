package cz.cvut.fukalhan.swap.eventsdata.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.eventsdata.domain.EventRepository
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.eventsdata.model.GroupChat

class FirebaseEventRepository : EventRepository {
    private val db = Firebase.firestore

    override suspend fun createEvent(event: Event): DataResponse<String> {
        return try {
            val eventRef = db.collection(EVENTS).document()
            val eventId = eventRef.id
            event.id = eventId

            eventRef.set(event)
            DataResponse.Success(eventId)
        } catch (e: FirebaseFirestoreException) {
            Log.e("createEvent", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun createGroupChat(groupChat: GroupChat): Response {
        return try {
            db.collection(GROUP_CHANNELS).document(groupChat.id).set(groupChat)
            Response.Success
        } catch (e: FirebaseFirestoreException) {
            Log.e("createGroupChat", "Exception $e")
            Response.Error
        }
    }
}
