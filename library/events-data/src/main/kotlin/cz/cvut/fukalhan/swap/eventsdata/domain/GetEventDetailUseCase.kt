package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.model.Event

class GetEventDetailUseCase(private val eventRepository: EventRepository) {
    suspend fun getEvent(eventId: String): DataResponse<Event> {
        return eventRepository.getEvent(eventId)
    }
}
