package cz.cvut.fukalhan.swap.system.service

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val USER_ID = "userId"
const val ITEM_ID = "itemId"
const val LIKED_ITEM_NOTIFICATION_INTENT = "itemLikedNotification"

class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Check if the user's token is the same as the new token
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Extract the data from the remote message
        val userId = remoteMessage.data[USER_ID]
        val itemId = remoteMessage.data[ITEM_ID]

        // Create an intent with the data and broadcast it to the MainActivity
        val intent = Intent(LIKED_ITEM_NOTIFICATION_INTENT)
        intent.putExtra(USER_ID, userId)
        intent.putExtra(ITEM_ID, itemId)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
