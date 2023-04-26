package cz.cvut.fukalhan.swap.profiledetail.di

import cz.cvut.fukalhan.swap.profiledetail.presentation.ProfileDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileDetailModule = module {
    viewModelOf(::ProfileDetailViewModel)
}
