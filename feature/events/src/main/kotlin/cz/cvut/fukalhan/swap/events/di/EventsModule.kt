package cz.cvut.fukalhan.swap.events.di

import cz.cvut.fukalhan.swap.events.presentation.AddEventViewModel
import cz.cvut.fukalhan.swap.events.presentation.EventListViewModel
import cz.cvut.fukalhan.swap.events.presentation.prediction.PredictionListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val eventsModule = module {
    viewModelOf(::EventListViewModel)
    viewModelOf(::AddEventViewModel)
    viewModelOf(::PredictionListViewModel)
}
