package cz.cvut.fukalhan.swap.events.model.eventdetail

import cz.cvut.fukalhan.swap.eventsdata.model.Location

data class EventDetailScreenVo(
    val name: String = "",
    val date: String = "",
    val description: String = "",
    val location: Location? = null,
    val organizerInfo: OrganizerInfoVo? = null,
    val isUserSubscribed: Boolean = false
)