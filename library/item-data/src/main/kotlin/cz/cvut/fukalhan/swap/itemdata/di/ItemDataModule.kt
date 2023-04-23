package cz.cvut.fukalhan.swap.itemdata.di

import cz.cvut.fukalhan.swap.itemdata.data.FirebaseChannelRepository
import cz.cvut.fukalhan.swap.itemdata.data.FirebaseImageRepository
import cz.cvut.fukalhan.swap.itemdata.data.FirebaseItemRepository
import cz.cvut.fukalhan.swap.itemdata.domain.CreateChannelUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemDetailUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemFromChannelUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.GetUserLikedItemsUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.SaveItemUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.ToggleItemLikeUseCase
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ChannelRepository
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ImageRepository
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ItemRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val itemDataModule = module {
    singleOf(::FirebaseItemRepository) bind ItemRepository::class
    singleOf(::FirebaseImageRepository) bind ImageRepository::class
    singleOf(::FirebaseChannelRepository) bind ChannelRepository::class
    factoryOf(::SaveItemUseCase)
    factoryOf(::GetUserItemsUseCase)
    factoryOf(::GetItemsUseCase)
    factoryOf(::GetItemDetailUseCase)
    factoryOf(::ToggleItemLikeUseCase)
    factoryOf(::GetUserLikedItemsUseCase)
    factoryOf(::CreateChannelUseCase)
    factoryOf(::GetItemFromChannelUseCase)
}
