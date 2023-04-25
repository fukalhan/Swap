package cz.cvut.fukalhan.swap.userdata.di

import cz.cvut.fukalhan.swap.userdata.data.FirebaseReviewRepository
import cz.cvut.fukalhan.swap.userdata.data.FirebaseUserRepository
import cz.cvut.fukalhan.swap.userdata.domain.AddReviewUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserProfileDataUseCase
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userDataModule = module {
    singleOf(::FirebaseUserRepository) bind UserRepository::class
    singleOf(::FirebaseReviewRepository) bind ReviewRepository::class
    factoryOf(::GetUserProfileDataUseCase)
    factoryOf(::AddReviewUseCase)
}
