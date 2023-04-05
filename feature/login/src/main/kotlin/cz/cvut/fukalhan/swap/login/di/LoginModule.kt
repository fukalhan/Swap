package cz.cvut.fukalhan.swap.login.di

import cz.cvut.fukalhan.swap.login.presentation.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val loginModule = module {
    viewModelOf(::SignUpViewModel)
}