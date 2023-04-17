package cz.cvut.fukalhan.swap.di

import cz.cvut.fukalhan.swap.additem.di.addItemModule
import cz.cvut.fukalhan.swap.auth.di.authModule
import cz.cvut.fukalhan.swap.itemdata.di.itemDataModule
import cz.cvut.fukalhan.swap.itemlist.di.itemListModule
import cz.cvut.fukalhan.swap.login.di.loginModule
import cz.cvut.fukalhan.swap.profile.di.profileModule
import cz.cvut.fukalhan.swap.userdata.di.userDataModule
import org.koin.core.context.startKoin

fun setupKoin() {
    startKoin {
        modules(
            listOf(
                loginModule,
                authModule,
                userDataModule,
                profileModule,
                itemDataModule,
                addItemModule,
                itemListModule
            )
        )
    }
}
