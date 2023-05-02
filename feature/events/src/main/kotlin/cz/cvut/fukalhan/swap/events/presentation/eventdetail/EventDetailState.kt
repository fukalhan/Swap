package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.tools.DateFormatter
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.eventsdata.model.Location

sealed class EventDetailState {
    object Init : EventDetailState()
    object Loading : EventDetailState()
    data class Success(
        val event: EventState
    ) : EventDetailState()

    data class AddParticipantToEventSuccess(val message: Int = R.string.participateSuccess) : EventDetailState()

    data class AddParticipantToEventFail(val message: Int = R.string.participateFail) : EventDetailState()

    data class RemoveParticipantFromEventSuccess(val message: Int = R.string.unsubscribeSuccess) : EventDetailState()

    data class RemoveParticipantFromEventFail(val message: Int = R.string.unsubscribeFail) : EventDetailState()

    data class Failure(val message: Int = R.string.cannotLoadEventData) : EventDetailState()
}

data class EventState(
    val id: String,
    val organizerId: String,
    val title: String,
    val date: String,
    val description: String,
    val location: Location,
    val participants: List<String>
)

internal fun Event.toEventState(stringResources: StringResources): EventDetailState {
    val dateFormatter = DateFormatter(stringResources)
    val date = dateFormatter.formatEventDate(selectedDays)
    return EventDetailState.Success(
        EventState(
            this.id,
            this.organizerId,
            this.title,
            date,
            this.description,
            this.location,
            this.participants
        )
    )
}
