package cz.cvut.fukalhan.swap.itemdetail.di

import cz.cvut.fukalhan.swap.itemdetail.presentation.ItemDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val itemDetailModule = module {
    viewModelOf(::ItemDetailViewModel)
}
