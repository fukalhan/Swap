package cz.cvut.fukalhan.swap.events.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.ComposeViewModel
import cz.cvut.fukalhan.design.presentation.GROUP_CHAT
import cz.cvut.fukalhan.design.presentation.UiState
import cz.cvut.fukalhan.design.presentation.showLoading
import cz.cvut.fukalhan.swap.events.model.addevent.AddEventScreenEvent
import cz.cvut.fukalhan.swap.events.model.addevent.AddEventScreenVo
import cz.cvut.fukalhan.swap.eventsdata.data.resolve
import cz.cvut.fukalhan.swap.eventsdata.domain.CreateEventChatUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.CreateEventUseCase
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.eventsdata.model.GroupChat
import cz.cvut.fukalhan.swap.eventsdata.model.Location
import cz.cvut.fukalhan.swap.placesdata.data.resolve
import cz.cvut.fukalhan.swap.placesdata.domain.GetPlaceDetailUseCase
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId

class AddEventViewModel(
    private val params: Params,
    private val chatClient: ChatClient,
    private val getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val createEventChatUseCase: CreateEventChatUseCase,
) : ComposeViewModel<AddEventScreenVo, AddEventScreenEvent>,
    ViewModel() {

    private val _viewState: MutableStateFlow<UiState<AddEventScreenVo>> = MutableStateFlow(
        UiState(
            data = AddEventScreenVo(),
            loading = true
        )
    )

    override val viewState: StateFlow<UiState<AddEventScreenVo>> = _viewState.asStateFlow()

    override fun onEvent(event: AddEventScreenEvent) {
        when (event) {
            AddEventScreenEvent.OnBackClick -> params.navigateBack
            is AddEventScreenEvent.EventNameChanged -> changeName(newName = event.newName)
            is AddEventScreenEvent.EventDescriptionChanged -> changeDescription(
                newDescription = event.newValue
            )
            is AddEventScreenEvent.ChangeDatePickerVisibility -> changeDatePickerVisibility(
                visible = event.visible
            )
            is AddEventScreenEvent.ChangeLocationPickerVisibility -> changeLocationPickerVisibility(
                visible = event.visible
            )
            AddEventScreenEvent.OnCancelEvent -> params.navigateBack
            AddEventScreenEvent.OnSaveEventClick -> createEvent()
        }
    }

    /**
     * Change event name to the [newName]
     */
    private fun changeName(newName: String) {
        _viewState.update {
            UiState(
                data = it.data.copy(
                    name = newName
                )
            )
        }
    }

    /**
     * Change event description to the [newDescription]
     */
    private fun changeDescription(newDescription: String) {
        if (newDescription.length <= AddEventScreenVo.DESCRIPTION_CHAR_LIMIT) {
            _viewState.update {
                UiState(
                    data = it.data.copy(
                        description = newDescription
                    )
                )
            }
        }
    }

    /**
     * Toggle date picker dialog visibility
     *
     * @param visible determine if the date picker is visible
     */
    private fun changeDatePickerVisibility(visible: Boolean) {
        _viewState.update {
            UiState(
                data = it.data.copy(
                    showDatePicker = visible
                )
            )
        }
    }

    /**
     * Toggle location picker bottom sheet visibility
     *
     * @param visible determine if the location picker bottom sheet is visible
     */
    private fun changeLocationPickerVisibility(visible: Boolean) {
       // TODO
    }

    fun getPlaceLocation(placeId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getPlaceDetailUseCase.getPlaceDetail(placeId).resolve(
                onSuccess = {
                    /*_addEventState.value = AddEventState.GetLocationSuccess(
                        LocationState(it.result.geometry.location)
                    )*/
                },
                onError = { /*_addEventState.value = AddEventState.GetLocationFail()*/ }
            )
        }
    }

    private fun createEvent() {
        _viewState.showLoading()
        viewModelScope.launch(Dispatchers.IO) {
            val selectedDaysAsLong = selectedDays.map {
                val localDateTime = it.atStartOfDay()
                val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
                zonedDateTime.toInstant().toEpochMilli()
            }
            val event = Event(
                organizerId = organizerId,
                title = title,
                description = description,
                selectedDays = selectedDaysAsLong,
                location = Location(location.lat, location.lng)
            )

            createEventUseCase.createEvent(event).resolve(
                onSuccess = {
                    //_addEventState.value = AddEventState.AddEventSuccess()
                    // createChannel(it, organizerId, title)
                },
                onError = { /*_addEventState.value = AddEventState.AddEventFail()*/ }
            )
        }
    }

    private fun createChannel(
        chatId: String,
        organizerId: String,
        eventTitle: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val groupChat = GroupChat(chatId, listOf(organizerId))
            createEventChatUseCase.createEventChat(groupChat).resolve(
                onSuccess = {
                    chatClient.createChannel(
                        channelId = chatId,
                        channelType = GROUP_CHAT,
                        memberIds = groupChat.members,
                        extraData = mapOf(
                            "name" to "Akce: $eventTitle",
                        )
                    ).enqueue { result ->
                        if (result.isSuccess) {
                            //_addEventState.value = AddEventState.AddEventSuccess()
                        } else {
                            Log.e("CreateEventChat", result.toString())
                            //_addEventState.value = AddEventState.CreateEventChatFail()
                            // TODO delete channel record from the db
                        }
                    }
                },
                onError = { /*_addEventState.value = AddEventState.CreateEventChatFail()*/ }
            )
        }
    }

    data class Params(
        val navigateBack: () -> Unit
    )
}
