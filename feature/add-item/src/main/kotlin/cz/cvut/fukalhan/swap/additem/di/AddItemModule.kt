package cz.cvut.fukalhan.swap.additem.di

import cz.cvut.fukalhan.swap.additem.viewmodel.AddItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val addItemModule = module {
    viewModelOf(::AddItemViewModel)
}
