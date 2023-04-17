package cz.cvut.fukalhan.swap.itemdata.di

import cz.cvut.fukalhan.swap.itemdata.data.FirebaseImageRepository
import cz.cvut.fukalhan.swap.itemdata.data.FirebaseItemRepository
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ImageRepository
import cz.cvut.fukalhan.swap.itemdata.domain.ItemRepository
import cz.cvut.fukalhan.swap.itemdata.domain.SaveItemUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val itemDataModule = module {
    singleOf(::FirebaseItemRepository) bind ItemRepository::class
    singleOf(::FirebaseImageRepository) bind ImageRepository::class
    factoryOf(::SaveItemUseCase)
    factoryOf(::GetUserItemsUseCase)
}