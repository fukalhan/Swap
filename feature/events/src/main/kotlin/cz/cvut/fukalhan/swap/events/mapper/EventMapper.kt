package cz.cvut.fukalhan.swap.events.mapper

import cz.cvut.fukalhan.swap.events.model.EventVo
import cz.cvut.fukalhan.swap.eventsdata.model.Event

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