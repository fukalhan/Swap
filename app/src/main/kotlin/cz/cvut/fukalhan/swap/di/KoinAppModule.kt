package cz.cvut.fukalhan.swap.di

import cz.cvut.fukalhan.design.di.designModule
import cz.cvut.fukalhan.swap.additem.di.addItemModule
import cz.cvut.fukalhan.swap.auth.di.authModule
import cz.cvut.fukalhan.swap.itemdata.di.itemDataModule
import cz.cvut.fukalhan.swap.itemdetail.di.itemDetailModule
import cz.cvut.fukalhan.swap.itemlist.di.itemListModule
import cz.cvut.fukalhan.swap.login.di.loginModule
import cz.cvut.fukalhan.swap.messages.di.messagesModule
import cz.cvut.fukalhan.swap.profile.di.profileModule
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
                itemDataModule,
                addItemModule,
                itemListModule,
                itemDetailModule,
                designModule,
                chatClientModule,
                messagesModule
            )
        )
    }
}
