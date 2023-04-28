package cz.cvut.fukalhan.swap.notifications.presentation

import android.net.Uri
import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.swap.notifications.R
import cz.cvut.fukalhan.swap.userdata.model.Notification

sealed class NotificationsState

object Init : NotificationsState()

object Loading : NotificationsState()

data class NewNotification(
    val data: NotificationState
) : NotificationsState()

data class NotificationState(
    val userId: String,
    val profilePic: Uri,
    val notificationMessage: String
)

internal fun Notification.toNotificationState(stringResources: StringResources): NewNotification {
    return NewNotification(
        NotificationState(
            this.userId,
            this.userProfilePic,
            stringResources.getString(R.string.userLikedItemMessage, this.username, this.itemName)
        )
    )
}

data class Failure(val message: Int = R.string.cannotLoadData) : NotificationsState()
