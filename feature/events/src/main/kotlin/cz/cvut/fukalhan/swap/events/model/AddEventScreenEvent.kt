package cz.cvut.fukalhan.swap.events.model

interface AddEventScreenEvent {

    data object OnBackClick : AddEventScreenEvent

    data object OnSaveEventClick : AddEventScreenEvent

    data object OnCancelEvent : AddEventScreenEvent

    data class EventNameChange(val newName: String) : AddEventScreenEvent
}