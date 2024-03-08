package cz.cvut.fukalhan.swap.domain.model

/**
 * Model for event
 *
 * @param id unique event identifier
 * @param organizerId organizer's identifier
 * @param title event's name
 * @param description event's description
 * @param selectedDays days on which the event takes place
 * @param location coordinates of the location of the event
 * @param participants ids of attending participants
 */
data class Event(
    var id: String = "",
    var organizerId: String = "",
    var title: String = "",
    var description: String = "",
    var selectedDays: List<Long> = emptyList(),
    var location: Location = Location(),
    var participants: List<String> = emptyList()
)
