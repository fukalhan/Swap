package cz.cvut.fukalhan.swap.system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.FirebaseApp
import cz.cvut.fukalhan.swap.notifications.presentation.NotificationsViewModel
import cz.cvut.fukalhan.swap.system.navigation.NavHost
import cz.cvut.fukalhan.swap.system.service.ITEM_ID
import cz.cvut.fukalhan.swap.system.service.LIKED_ITEM_NOTIFICATION_INTENT
import cz.cvut.fukalhan.swap.system.service.USER_ID
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    val notificationsViewModel: NotificationsViewModel by inject()

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val userId = p1?.getStringExtra(USER_ID)
            val itemId = p1?.getStringExtra(ITEM_ID)

            if (userId != null && itemId != null) {
                notificationsViewModel.getNotification(userId, itemId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseApp
        FirebaseApp.initializeApp(this)

        LocalBroadcastManager.getInstance(this).registerReceiver(
            messageReceiver,
            IntentFilter(LIKED_ITEM_NOTIFICATION_INTENT)
        )

        setContent {
            MaterialTheme {
                NavHost()
            }
        }
    }
}
