package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.Response

class AddParticipantToEventUseCase(private val eventRepository: EventRepository) {

    suspend fun addParticipantToEvent(eventId: String, userId: String): Response {
        return eventRepository.addParticipantToEvent(eventId, userId)
    }
}
