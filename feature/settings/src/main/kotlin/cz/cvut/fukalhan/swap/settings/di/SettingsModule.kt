package cz.cvut.fukalhan.swap.settings.di

import cz.cvut.fukalhan.swap.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}
