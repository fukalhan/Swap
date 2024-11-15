package cz.cvut.fukalhan.swap.events.mapper

import cz.cvut.fukalhan.design.presentation.StringModel
import cz.cvut.fukalhan.design.tools.formatDate
import cz.cvut.fukalhan.swap.events.R
import cz.cvut.fukalhan.swap.events.model.eventdetail.OrganizerInfoVo
import cz.cvut.fukalhan.swap.events.model.eventlist.EventVo
import cz.cvut.fukalhan.swap.eventsdata.model.Event
import cz.cvut.fukalhan.swap.userdata.model.User

/**
 * Map list of domain [Event] to list of [EventVo]
 */
internal fun List<Event>.toListEventVo(): List<EventVo> {
    return map {
        it.toEventVo()
    }
}

/**
 * Map domain [Event] to [EventVo]
 */
private fun Event.toEventVo(): EventVo {
    return EventVo(
        id = id,
        name = title,
        description = description,
        // TODO formatter
        date = "Date",
        // TODO location name
        location = "Location"
    )
}

internal fun User.toOrganizerVo() =
    OrganizerInfoVo(
        id = id,
        profilePic = profilePicUri,
        username = username,
        joinDate = StringModel.Resource(R.string.memberSince, formatDate(joinDate)),
        rating = rating
    )