package cz.cvut.fukalhan.design.di

import cz.cvut.fukalhan.design.presentation.StringResources
import cz.cvut.fukalhan.design.system.AndroidStringResources
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val designModule = module {
    factoryOf(::AndroidStringResources) bind StringResources::class
}
