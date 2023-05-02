package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.model.Event

class CreateEventUseCase(private val eventRepository: EventRepository) {
    suspend fun createEvent(event: Event): DataResponse<String> {
        return eventRepository.createEvent(event)
    }
}
