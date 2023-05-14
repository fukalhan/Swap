package cz.cvut.fukalhan.swap.events.presentation.addevent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.GROUP_CHAT
import cz.cvut.fukalhan.swap.eventsdata.data.DataResponse
import cz.cvut.fukalhan.swap.eventsdata.domain.CreateEventChatUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.CreateEventUseCase
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.eventsdata.model.GroupChat
import cz.cvut.fukalhan.swap.eventsdata.model.Location
import cz.cvut.fukalhan.swap.placesdata.data.placedetail.Coordinates
import cz.cvut.fukalhan.swap.placesdata.data.resolve
import cz.cvut.fukalhan.swap.placesdata.domain.GetPlaceDetailUseCase
import io.getstream.chat.android.client.ChatClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

class AddEventViewModel(
    private val chatClient: ChatClient,
    private val getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val createEventUseCase: CreateEventUseCase,
    private val createEventChatUseCase: CreateEventChatUseCase,
) : ViewModel() {
    private val _addEventState: MutableStateFlow<AddEventState> = MutableStateFlow(AddEventState.Init)
    val addEventState: StateFlow<AddEventState>
        get() = _addEventState

    fun getPlaceLocation(placeId: String) {
        _addEventState.value = AddEventState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            getPlaceDetailUseCase.getPlaceDetail(placeId).resolve(
                onSuccess = {
                    _addEventState.value = AddEventState.GetLocationSuccess(
                        LocationState(it.result.geometry.location)
                    )
                },
                onError = { _addEventState.value = AddEventState.GetLocationFail() }
            )
        }
    }

    fun createEvent(
        title: String,
        description: String,
        selectedDays: List<LocalDate>,
        organizerId: String,
        location: Coordinates
    ) {
        _addEventState.value = AddEventState.Loading
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

            val response = createEventUseCase.createEvent(event)
            when (response) {
                is DataResponse.Success -> {
                    response.data?.let { eventId ->
                        _addEventState.value = AddEventState.AddEventSuccess()
                        // createChannel(eventId, organizerId, title)
                    } ?: run {
                        _addEventState.value = AddEventState.AddEventFail()
                    }
                }
                else -> _addEventState.value = AddEventState.AddEventFail()
            }
        }
    }

    private fun createChannel(
        chatId: String,
        organizerId: String,
        eventTitle: String,
    ) {
        _addEventState.value = AddEventState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val groupChat = GroupChat(chatId, listOf(organizerId))
            val response = createEventChatUseCase.createEventChat(groupChat)
            when (response) {
                is cz.cvut.fukalhan.swap.eventsdata.data.Response.Success -> {
                    chatClient.createChannel(
                        channelId = chatId,
                        channelType = GROUP_CHAT,
                        memberIds = groupChat.members,
                        extraData = mapOf(
                            "name" to "Akce: $eventTitle",
                        )
                    ).enqueue { result ->
                        if (result.isSuccess) {
                            _addEventState.value = AddEventState.AddEventSuccess()
                        } else {
                            Log.e("nfkdfn", result.toString())
                            _addEventState.value = AddEventState.CreateEventChatFail()
                            // TODO delete channel record from the db
                        }
                    }
                }
                else -> _addEventState.value = AddEventState.CreateEventChatFail()
            }
        }
    }

    fun setStateToInit() {
        _addEventState.value = AddEventState.Init
    }
}
