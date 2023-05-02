package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.Response
import cz.cvut.fukalhan.swap.eventsdata.model.GroupChat

class CreateEventChatUseCase(private val eventRepository: EventRepository) {

    suspend fun createEventChat(groupChat: GroupChat): Response {
        return eventRepository.createGroupChat(groupChat)
    }
}
