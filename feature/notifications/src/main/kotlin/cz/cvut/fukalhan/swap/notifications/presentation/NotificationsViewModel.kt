package cz.cvut.fukalhan.swap.notifications.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.userdata.domain.GetNotificationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val getNotificationUseCase: GetNotificationUseCase,
    private val stringResources: StringResources
) : ViewModel() {
    private val _notifications = MutableStateFlow(emptyList<NotificationState>())
    val notifications: StateFlow<List<NotificationState>>
        get() = _notifications

    private val _notificationState: MutableStateFlow<NotificationsState> = MutableStateFlow(Init)
    val notificationsState: StateFlow<NotificationsState>
        get() = _notificationState

    fun getNotification(userId: String, itemId: String) {
        _notificationState.value = Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getNotificationUseCase.getNotification(userId, itemId)
            response.data?.let {
                val notification = it.toNotificationState(stringResources)
                _notificationState.value = it.toNotificationState(stringResources)
                _notifications.value += notification.data
            } ?: run {
                _notificationState.value = Failure()
            }
        }
    }

    fun setStateToInit() {
        _notificationState.value = Init
    }
}
