package cz.cvut.fukalhan.swap.events.model.addevent

sealed interface AddEventScreenEvent {

    data object OnBackClick : AddEventScreenEvent

    data object OnSaveEventClick : AddEventScreenEvent

    data object OnCancelEvent : AddEventScreenEvent

    data class EventNameChanged(val newName: String) : AddEventScreenEvent

    data class EventDescriptionChanged(val newValue: String) : AddEventScreenEvent

    data class ChangeDatePickerVisibility(val visible: Boolean) : AddEventScreenEvent

    data class ChangeLocationPickerVisibility(val visible: Boolean) : AddEventScreenEvent
}