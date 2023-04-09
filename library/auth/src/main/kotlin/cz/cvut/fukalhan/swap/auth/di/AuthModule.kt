package cz.cvut.fukalhan.swap.auth.di

import cz.cvut.fukalhan.swap.auth.data.FirebaseRepository
import cz.cvut.fukalhan.swap.auth.domain.Repository
import cz.cvut.fukalhan.swap.auth.domain.SignInUseCase
import cz.cvut.fukalhan.swap.auth.domain.SignUpUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::FirebaseRepository) bind Repository::class
    singleOf(::SignUpUseCase)
    singleOf(::SignInUseCase)
}
