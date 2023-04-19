package cz.cvut.fukalhan.swap.itemlist.di

import cz.cvut.fukalhan.swap.itemlist.presentation.ItemListViewModel
import cz.cvut.fukalhan.swap.itemlist.presentation.itemdetail.ItemDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val itemListModule = module {
    viewModelOf(::ItemListViewModel)
    viewModelOf(::ItemDetailViewModel)
}
