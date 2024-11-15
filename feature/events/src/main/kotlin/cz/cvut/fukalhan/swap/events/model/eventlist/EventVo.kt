package cz.cvut.fukalhan.swap.events.model.eventlist

/**
 * View object for event list card
 *
 * @property id event id
 * @property name event name
 * @property date date of the event
 * @property location location of the event
 * @property description the event description
 */
data class EventVo(
    val id: String,
    val name: String,
    val date: String,
    val location: String,
    val description: String,
)