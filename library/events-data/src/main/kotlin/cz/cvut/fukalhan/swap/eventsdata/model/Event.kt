package cz.cvut.fukalhan.swap.eventsdata.model

data class Event(
    var id: String = "",
    var organizerId: String = "",
    var title: String = "",
    var description: String = "",
    var selectedDays: List<Long> = emptyList(),
    var location: Location = Location()
)
