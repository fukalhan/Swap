package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.tools.DateFormatter
import cz.cvut.fukalhan.swap.eventsdata.model.Event

sealed class EventDetailState {
    object Init : EventDetailState()
    object Loading : EventDetailState()
    data class Success(
        val event: EventState
    ) : EventDetailState()
    data class Failure(val message: Int = R.string.cannotLoadEventData) : EventDetailState()
}

data class EventState(
    val id: String,
    val title: String,
    val date: String,
    val description: String,
)

internal fun Event.toEventState(stringResources: StringResources): EventDetailState {
    val dateFormatter = DateFormatter(stringResources)
    val date = dateFormatter.formatEventDate(selectedDays)
    return EventDetailState.Success(
        EventState(
            this.id,
            this.title,
            date,
            this.description
        )
    )
}
