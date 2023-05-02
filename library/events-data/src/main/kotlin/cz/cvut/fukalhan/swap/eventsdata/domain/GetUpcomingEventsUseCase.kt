package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.model.Event

class GetUpcomingEventsUseCase(private val eventRepository: EventRepository) {

    suspend fun getUpcomingEvents(date: Long): DataResponse<List<Event>> {
        return eventRepository.getUpcomingEvents(date)
    }
}
