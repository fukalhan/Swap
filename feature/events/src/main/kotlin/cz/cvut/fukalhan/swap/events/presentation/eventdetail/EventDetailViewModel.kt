package cz.cvut.fukalhan.swap.events.presentation.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.domain.GetEventDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val stringResources: StringResources,
    private val getEventDetailUseCase: GetEventDetailUseCase
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
}
