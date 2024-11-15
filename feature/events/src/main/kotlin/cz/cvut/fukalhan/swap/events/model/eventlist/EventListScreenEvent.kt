package cz.cvut.fukalhan.swap.events.model.eventlist

sealed interface EventListScreenEvent {

    data object OnAddEventClick : EventListScreenEvent

    data class OnEventClick(val id: String) : EventListScreenEvent
}