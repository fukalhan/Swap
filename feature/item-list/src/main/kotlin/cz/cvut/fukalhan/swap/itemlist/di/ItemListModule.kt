package cz.cvut.fukalhan.swap.itemlist.di

import cz.cvut.fukalhan.swap.itemlist.viewmodel.ItemListViewModel
import cz.cvut.fukalhan.swap.itemlist.viewmodel.ItemSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val itemListModule = module {
    viewModelOf(::ItemListViewModel)
    viewModelOf(::ItemSearchViewModel)
}
