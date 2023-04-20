package cz.cvut.fukalhan.swap.profile.di

import cz.cvut.fukalhan.swap.profile.presentation.items.LikedItemListViewModel
import cz.cvut.fukalhan.swap.profile.presentation.items.UserItemsViewModel
import cz.cvut.fukalhan.swap.profile.presentation.profileinfo.ProfileInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileInfoViewModel)
    viewModelOf(::UserItemsViewModel)
    viewModelOf(::LikedItemListViewModel)
}
