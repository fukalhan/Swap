package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.data.Response
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.eventsdata.model.GroupChat

interface EventRepository {
    suspend fun createEvent(event: Event): DataResponse<String>

    suspend fun createGroupChat(groupChat: GroupChat): Response

    suspend fun getUpcomingEvents(date: Long): DataResponse<List<Event>>

    suspend fun getEvent(eventId: String): DataResponse<Event>

    suspend fun addParticipantToEvent(eventId: String, userId: String): Response

    suspend fun removeParticipantFromEvent(eventId: String, userId: String): Response
}
