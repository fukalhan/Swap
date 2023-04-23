package cz.cvut.fukalhan.swap.messages.di

import cz.cvut.fukalhan.swap.messages.presentation.ChatViewModelFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val messagesModule = module {
    singleOf(::ChatViewModelFactory)
}
