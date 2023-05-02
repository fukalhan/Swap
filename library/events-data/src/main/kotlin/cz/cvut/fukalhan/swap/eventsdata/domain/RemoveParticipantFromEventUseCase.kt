package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.Response

class RemoveParticipantFromEventUseCase(private val eventRepository: EventRepository) {
    suspend fun removeParticipantFromEvent(eventId: String, userId: String): Response {
        return eventRepository.removeParticipantFromEvent(eventId, userId)
    }
}
