package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.eventsdata.data.resolve
import cz.cvut.fukalhan.swap.eventsdata.domain.AddParticipantToEventUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.GetEventDetailUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.RemoveParticipantFromEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val stringResources: StringResources,
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val addParticipantToEventUseCase: AddParticipantToEventUseCase,
    private val removeParticipantFromEventUseCase: RemoveParticipantFromEventUseCase
) : ViewModel() {
    private val _eventDetailState: MutableStateFlow<EventDetailState> = MutableStateFlow(EventDetailState.Init)
    val eventDetailState: StateFlow<EventDetailState>
        get() = _eventDetailState

    fun getEventDetail(eventId: String) {
        _eventDetailState.value = EventDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getEventDetailUseCase.getEvent(eventId).resolve(
                onSuccess = { _eventDetailState.value = it.toEventState(stringResources) },
                onError = { _eventDetailState.value = EventDetailState.Failure() }
            )
        }
    }

    fun addParticipantToEvent(eventId: String, userId: String) {
        _eventDetailState.value = EventDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            addParticipantToEventUseCase.addParticipantToEvent(eventId, userId).resolve(
                onSuccess = { _eventDetailState.value = EventDetailState.AddParticipantToEventSuccess() },
                onError = { _eventDetailState.value = EventDetailState.AddParticipantToEventFail() }
            )
            getEventDetail(eventId)
        }
    }

    fun removeParticipantFromEvent(eventId: String, userId: String) {
        _eventDetailState.value = EventDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            removeParticipantFromEventUseCase.removeParticipantFromEvent(eventId, userId).resolve(
                onSuccess = { _eventDetailState.value = EventDetailState.RemoveParticipantFromEventSuccess() },
                onError = { _eventDetailState.value = EventDetailState.RemoveParticipantFromEventFail() }
            )

            getEventDetail(eventId)
        }
    }
}
