package cz.cvut.fukalhan.swap.eventsdata.data

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import cz.cvut.fukalhan.swap.eventsdata.domain.EventRepository
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.eventsdata.model.GroupChat
import kotlinx.coroutines.tasks.await

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

    override suspend fun getUpcomingEvents(date: Long): DataResponse<List<Event>> {
        return try {
            val querySnapshot = db
                .collection(EVENTS)
                .orderBy(SELECTED_DAYS)
                .get()
                .await()

            val events = if (querySnapshot.isEmpty) {
                emptyList()
            } else {
                querySnapshot.documents.mapNotNull { doc ->
                    doc.toObject(Event::class.java)
                }.filter { event ->
                    // Filter because dumb Firestore doesn't work
                    event.selectedDays.any {
                        it > date
                    }
                }
            }
            DataResponse.Success(events)
        } catch (e: Exception) {
            Log.e("getUpcomingEvents", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun getEvent(eventId: String): DataResponse<Event> {
        return try {
            val doc = db.collection(EVENTS).document(eventId).get().await()
            if (doc.exists()) {
                val event = doc.toObject(Event::class.java)
                event?.let {
                    DataResponse.Success(it)
                } ?: run {
                    DataResponse.Error()
                }
            } else {
                DataResponse.Error()
            }
        } catch (e: Exception) {
            Log.e("getEvent", "Exception $e")
            DataResponse.Error()
        }
    }

    override suspend fun addParticipantToEvent(eventId: String, userId: String): Response {
        return try {
            val eventRef = db.collection(EVENTS).document(eventId)
            eventRef.update(PARTICIPANTS, FieldValue.arrayUnion(userId)).await()
            Response.Success
        } catch (e: Exception) {
            Log.e("addParticipantToEvent", "Exception $e")
            Response.Error
        }
    }

    override suspend fun removeParticipantFromEvent(eventId: String, userId: String): Response {
        return try {
            val eventRef = db.collection(EVENTS).document(eventId)
            eventRef.update(PARTICIPANTS, FieldValue.arrayRemove(userId)).await()
            Response.Success
        } catch (e: Exception) {
            Log.e("removeParticipantFromEvent", "Exception $e")
            Response.Error
        }
    }
}
