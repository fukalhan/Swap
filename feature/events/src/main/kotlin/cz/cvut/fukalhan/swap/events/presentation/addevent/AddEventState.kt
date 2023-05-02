package cz.cvut.fukalhan.swap.events.presentation.addevent

import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.placesdata.data.placedetail.Coordinates

sealed class AddEventState {
    object Init : AddEventState()

    object Loading : AddEventState()

    data class GetLocationSuccess(
        val location: LocationState,
        val message: Int = R.string.addressSaved
    ) : AddEventState()

    data class GetLocationFail(val message: Int = R.string.getLocationFail) : AddEventState()

    data class AddEventSuccess(val message: Int = R.string.addEventSuccess) : AddEventState()

    data class AddEventFail(val message: Int = R.string.addEventFail) : AddEventState()

    data class CreateEventChatFail(val message: Int = R.string.createEventChatFail) : AddEventState()
}

data class LocationState(
    val coordinate: Coordinates? = null
)
