package cz.cvut.fukalhan.swap.profiledetail.di

import cz.cvut.fukalhan.swap.profiledetail.presentation.items.ItemListViewModel
import cz.cvut.fukalhan.swap.profiledetail.presentation.review.ReviewListViewModel
import cz.cvut.fukalhan.swap.profiledetail.presentation.user.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileDetailModule = module {
    viewModelOf(::UserInfoViewModel)
    viewModelOf(::ReviewListViewModel)
    viewModelOf(::ItemListViewModel)
}
