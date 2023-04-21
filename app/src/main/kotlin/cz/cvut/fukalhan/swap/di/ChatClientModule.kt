package cz.cvut.fukalhan.swap.di

import cz.cvut.fukalhan.swap.BuildConfig
import io.getstream.chat.android.client.ChatClient
import org.koin.dsl.module

val chatClientModule = module {
    single {
        ChatClient.Builder(
            BuildConfig.STREAM_CHAT_API_KEY,
            get()
        ).build()
    }
}
