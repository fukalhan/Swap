package cz.cvut.fukalhan.swap.events.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.eventsdata.data.resolve
import cz.cvut.fukalhan.swap.eventsdata.domain.GetUpcomingEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

class EventListViewModel(
    private val stringResources: StringResources,
    private val getUpcomingEventsUseCase: GetUpcomingEventsUseCase
) : ViewModel() {
    private val _evenListState: MutableStateFlow<EventListState> = MutableStateFlow(EventListState.Init)
    val eventListState: StateFlow<EventListState>
        get() = _evenListState

    fun getEvents() {
        _evenListState.value = EventListState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            // Get current time as Long
            val time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            getUpcomingEventsUseCase.getUpcomingEvents(time).resolve(
                onSuccess = { events ->
                    if (events.isNotEmpty()) {
                        _evenListState.value = EventListState.Success(
                            events = events.map { event ->
                                event.toEventState(stringResources)
                            }
                        )
                    } else {
                        _evenListState.value = EventListState.Empty()
                    }
                },
                onError = { _evenListState.value = EventListState.Failure() }
            )
        }
    }
}
