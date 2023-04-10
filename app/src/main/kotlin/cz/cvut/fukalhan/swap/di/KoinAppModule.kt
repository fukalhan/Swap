package cz.cvut.fukalhan.swap.di

import cz.cvut.fukalhan.swap.auth.di.authModule
import cz.cvut.fukalhan.swap.login.di.loginModule
import cz.cvut.fukalhan.swap.userdata.di.userDataModule
import org.koin.core.context.startKoin

fun setupKoin() {
    startKoin {
        modules(listOf(loginModule, authModule, userDataModule))
    }
}
