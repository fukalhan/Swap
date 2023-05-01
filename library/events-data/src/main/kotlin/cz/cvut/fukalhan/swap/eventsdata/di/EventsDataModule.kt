package cz.cvut.fukalhan.swap.eventsdata.di

import cz.cvut.fukalhan.swap.eventsdata.data.FirebaseEventRepository
import cz.cvut.fukalhan.swap.eventsdata.domain.CreateEventChatUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.CreateEventUseCase
import cz.cvut.fukalhan.swap.eventsdata.domain.EventRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val eventsDataModule = module {
    singleOf(::FirebaseEventRepository) bind EventRepository::class
    factoryOf(::CreateEventUseCase)
    factoryOf(::CreateEventChatUseCase)
}
