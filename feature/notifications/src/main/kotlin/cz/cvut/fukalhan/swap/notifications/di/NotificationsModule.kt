package cz.cvut.fukalhan.swap.notifications.di

import cz.cvut.fukalhan.swap.notifications.presentation.NotificationsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val notificationsModule = module {
    singleOf(::NotificationsViewModel)
}
