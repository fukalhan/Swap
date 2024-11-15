package cz.cvut.fukalhan.swap.events.model.eventdetail

sealed interface EventDetailScreenEvent {

    data object OnBackClick : EventDetailScreenEvent

    data class OnOrganizerProfileClick(val id: String) : EventDetailScreenEvent

    data class UserSubscriptionChanged(val isSubscribed: Boolean) : EventDetailScreenEvent
}