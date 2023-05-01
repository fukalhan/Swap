package cz.cvut.fukalhan.swap.di

import cz.cvut.fukalhan.design.di.designModule
import cz.cvut.fukalhan.swap.additem.di.addItemModule
import cz.cvut.fukalhan.swap.auth.di.authModule
import cz.cvut.fukalhan.swap.events.di.eventsModule
import cz.cvut.fukalhan.swap.itemdata.di.itemDataModule
import cz.cvut.fukalhan.swap.itemdetail.di.itemDetailModule
import cz.cvut.fukalhan.swap.itemlist.di.itemListModule
import cz.cvut.fukalhan.swap.login.di.loginModule
import cz.cvut.fukalhan.swap.messages.di.messagesModule
import cz.cvut.fukalhan.swap.notifications.di.notificationsModule
import cz.cvut.fukalhan.swap.placesdata.di.placesDataModule
import cz.cvut.fukalhan.swap.profile.di.profileModule
import cz.cvut.fukalhan.swap.profiledetail.di.profileDetailModule
import cz.cvut.fukalhan.swap.review.di.reviewModule
import cz.cvut.fukalhan.swap.settings.di.settingsModule
import cz.cvut.fukalhan.swap.system.App
import cz.cvut.fukalhan.swap.userdata.di.userDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

fun setupKoin(app: App) {
    startKoin {
        androidContext(app)
        modules(
            listOf(
                loginModule,
                authModule,
                userDataModule,
                profileModule,
                profileDetailModule,
                itemDataModule,
                addItemModule,
                itemListModule,
                itemDetailModule,
                reviewModule,
                designModule,
                chatClientModule,
                messagesModule,
                notificationsModule,
                settingsModule,
                eventsModule,
                placesDataModule
            )
        )
    }
}
