package cz.cvut.fukalhan.swap.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.swap.events.model.eventdetail.EventDetailScreenEvent
import cz.cvut.fukalhan.swap.events.model.eventdetail.EventDetailScreenVo
import cz.cvut.fukalhan.swap.events.presentation.eventdetail.EventDetailState
import cz.cvut.fukalhan.swap.eventsdata.data.resolve
import cz.cvut.fukalhan.swap.eventsdata.domain.AddParticipantToEventUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.GetEventDetailUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.RemoveParticipantFromEventUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val params: Params,
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val addParticipantToEventUseCase: AddParticipantToEventUseCase,
    private val removeParticipantFromEventUseCase: RemoveParticipantFromEventUseCase
) : ViewModel(),
    ComposeViewModel<EventDetailScreenVo, EventDetailScreenEvent> {

    private val _viewState = MutableStateFlow(
        UiState(
            data = EventDetailScreenVo(),
            loading = true
        )
    )

    override val viewState: StateFlow<UiState<EventDetailScreenVo>> = _viewState.asStateFlow()

    init {
        getEventDetail(id = params.eventId)
    }

    override fun onEvent(event: EventDetailScreenEvent) {
        when (event) {
            EventDetailScreenEvent.OnBackClick -> params.navigateBack
            is EventDetailScreenEvent.OnOrganizerProfileClick -> navigateToUserProfile(
                userId = event.id
            )
            is EventDetailScreenEvent.UserSubscriptionChanged -> changeUserSubscriptionToEvent(
                isSubscribed = event.isSubscribed
            )
        }
    }

    private val _eventDetailState: MutableStateFlow<EventDetailState> = MutableStateFlow(
        EventDetailState.Init
    )

    val eventDetailState: StateFlow<EventDetailState>
        get() = _eventDetailState

    init {
        getEventDetail(id = params.eventId)
    }

    /**
     * Load event details
     *
     * @param id event id
     */
    private fun getEventDetail(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getEventDetailUseCase.getEvent(id).resolve(
                onSuccess = {
                    // TODO show event detail info
                },
                onError = {
                    // TODO error dialog
                }
            )
        }
    }

    /**
     * Navigate to user profile based on the [userId]
     *
     * @param userId user's id
     */
    private fun navigateToUserProfile(userId: String) {
        params.navigateToUserProfile(userId)
    }

    /**
     * Change user's registration to the event
     *
     * @param isSubscribed determine if the user is subscribing to the event
     */
    private fun changeUserSubscriptionToEvent(isSubscribed: Boolean) {
        // TODO
    }

    fun addParticipantToEvent(eventId: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addParticipantToEventUseCase.addParticipantToEvent(eventId, userId).resolve(
                onSuccess = { _eventDetailState.value =
                    EventDetailState.AddParticipantToEventSuccess()
                },
                onError = { _eventDetailState.value = EventDetailState.AddParticipantToEventFail() }
            )
            getEventDetail(eventId)
        }
    }

    fun removeParticipantFromEvent(eventId: String, userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeParticipantFromEventUseCase.removeParticipantFromEvent(eventId, userId).resolve(
                onSuccess = { _eventDetailState.value =
                    EventDetailState.RemoveParticipantFromEventSuccess()
                },
                onError = { _eventDetailState.value =
                    EventDetailState.RemoveParticipantFromEventFail()
                }
            )

            getEventDetail(eventId)
        }
    }

    data class Params(
        val eventId: String,
        val navigateBack: () -> Unit,
        val navigateToUserProfile: (String) -> Unit
    )
}
