package cz.cvut.fukalhan.swap.review.di

import cz.cvut.fukalhan.swap.review.presentation.ReviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val reviewModule = module {
    viewModelOf(::ReviewViewModel)
}
