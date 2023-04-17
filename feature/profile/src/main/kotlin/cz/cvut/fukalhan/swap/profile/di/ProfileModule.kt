package cz.cvut.fukalhan.swap.profile.di

import cz.cvut.fukalhan.swap.profile.presentation.ProfileViewModel
import cz.cvut.fukalhan.swap.profile.presentation.useritems.UserItemsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)
    viewModelOf(::UserItemsViewModel)
}
