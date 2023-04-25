package cz.cvut.fukalhan.swap.auth.di

import cz.cvut.fukalhan.swap.auth.data.FirebaseAuthRepository
import cz.cvut.fukalhan.swap.auth.domain.AuthRepository
import cz.cvut.fukalhan.swap.auth.domain.GetStreamChatUserTokenUseCase
import cz.cvut.fukalhan.swap.auth.domain.SignInUseCase
import cz.cvut.fukalhan.swap.auth.domain.SignUpUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::FirebaseAuthRepository) bind AuthRepository::class
    factoryOf(::SignUpUseCase)
    factoryOf(::SignInUseCase)
    factoryOf(::GetStreamChatUserTokenUseCase)
}
