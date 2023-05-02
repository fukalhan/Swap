package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.data.Response
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
            val response = getEventDetailUseCase.getEvent(eventId)

            when (response) {
                is DataResponse.Success -> {
                    response.data?.let { event ->
                        _eventDetailState.value = event.toEventState(stringResources)
                    } ?: run {
                        _eventDetailState.value = EventDetailState.Failure()
                    }
                }
                else -> _eventDetailState.value = EventDetailState.Failure()
            }
        }
    }

    fun addParticipantToEvent(eventId: String, userId: String) {
        _eventDetailState.value = EventDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = addParticipantToEventUseCase.addParticipantToEvent(eventId, userId)
            when (response) {
                is Response.Success -> _eventDetailState.value = EventDetailState.AddParticipantToEventSuccess()
                else -> _eventDetailState.value = EventDetailState.AddParticipantToEventFail()
            }

            getEventDetail(eventId)
        }
    }

    fun removeParticipantFromEvent(eventId: String, userId: String) {
        _eventDetailState.value = EventDetailState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = removeParticipantFromEventUseCase.removeParticipantFromEvent(eventId, userId)
            when (response) {
                is Response.Success -> _eventDetailState.value = EventDetailState.RemoveParticipantFromEventSuccess()
                else -> _eventDetailState.value = EventDetailState.RemoveParticipantFromEventFail()
            }

            getEventDetail(eventId)
        }
    }
}
