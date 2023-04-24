package cz.cvut.fukalhan.swap.messages.di

import cz.cvut.fukalhan.swap.messages.presentation.ChatViewModelFactory
import cz.cvut.fukalhan.swap.messages.presentation.ItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val messagesModule = module {
    singleOf(::ChatViewModelFactory)
    viewModelOf(::ItemViewModel)
}
