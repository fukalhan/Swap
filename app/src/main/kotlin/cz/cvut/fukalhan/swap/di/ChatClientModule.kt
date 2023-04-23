package cz.cvut.fukalhan.swap.di

import cz.cvut.fukalhan.swap.BuildConfig
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.notifications.handler.NotificationConfig
import io.getstream.chat.android.offline.model.message.attachments.UploadAttachmentsNetworkType
import io.getstream.chat.android.offline.plugin.configuration.Config
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.pushprovider.firebase.FirebasePushDeviceGenerator
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val chatClientModule = module {
    single {
        val context = androidContext()

        val pluginFactory = StreamOfflinePluginFactory(
            config = Config(
                backgroundSyncEnabled = true,
                userPresence = true,
                persistenceEnabled = true,
                uploadAttachmentsNetworkType = UploadAttachmentsNetworkType.NOT_ROAMING,
                useSequentialEventHandler = false,
            ),
            appContext = context,
        )
        val notificationConfig = NotificationConfig(
            pushDeviceGenerators = listOf(FirebasePushDeviceGenerator())
        )

        ChatClient.Builder(BuildConfig.STREAM_CHAT_API_KEY, get())
            .notifications(notificationConfig)
            .withPlugin(pluginFactory)
            .build()
    }
}
