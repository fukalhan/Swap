package cz.cvut.fukalhan.swap.events.presentation

import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.tools.DateFormatter
import cz.cvut.fukalhan.swap.eventsdata.model.Event

sealed class EventListState {
    object Init : EventListState()

    object Loading : EventListState()

    data class Success(
        val events: List<EventState>
    ) : EventListState()

    data class Empty(val message: Int = R.string.eventListEmpty) : EventListState()

    data class Failure(val message: Int = R.string.addEventFail) : EventListState()
}

data class EventState(
    val id: String,
    val title: String,
    val date: String,
    val description: String,
)

internal fun Event.toEventState(stringResources: StringResources): EventState {
    val dateFormatter = DateFormatter(stringResources)
    val date = dateFormatter.formatEventDate(selectedDays)
    return EventState(
        this.id,
        this.title,
        date,
        this.description
    )
}
