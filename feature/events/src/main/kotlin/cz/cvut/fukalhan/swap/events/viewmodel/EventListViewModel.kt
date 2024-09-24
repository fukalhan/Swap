package cz.cvut.fukalhan.swap.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.swap.events.model.EventListScreenEvent
import cz.cvut.fukalhan.swap.events.model.EventListScreenVo
import cz.cvut.fukalhan.swap.events.mapper.toListEventVo
import cz.cvut.fukalhan.swap.eventsdata.data.resolve
import cz.cvut.fukalhan.swap.eventsdata.domain.GetUpcomingEventsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId

class EventListViewModel(
    private val params: Params,
    private val getUpcomingEventsUseCase: GetUpcomingEventsUseCase
) : ViewModel(),
    ComposeViewModel<EventListScreenVo, EventListScreenEvent> {

    private val _viewState = MutableStateFlow(
        UiState(
            data = EventListScreenVo(),
            loading = true
        )
    )

    override val viewState: StateFlow<UiState<EventListScreenVo>> = _viewState.asStateFlow()

    override fun onEvent(event: EventListScreenEvent) {
        when (event) {
            EventListScreenEvent.OnAddEventClick -> params.navigateToAddEvent
            is EventListScreenEvent.OnEventClick -> params.navigateToEventDetail(event.id)
        }
    }

    init {
        getEvents()
    }

    private fun getEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            // Get current time as Long
            val time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            getUpcomingEventsUseCase.getUpcomingEvents(time).resolve(
                onSuccess = { events ->
                    _viewState.update {
                        UiState(
                            data = it.data.copy(
                                events = events.toListEventVo()
                            )
                        )
                    }
                },
                onError = {
                    // TODO display error message
                }
            )
        }
    }

    data class Params(
        val navigateToEventDetail: (String) -> Unit,
        val navigateToAddEvent: () -> Unit
    )
}
