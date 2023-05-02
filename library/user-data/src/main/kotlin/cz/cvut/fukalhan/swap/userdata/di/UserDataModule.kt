package cz.cvut.fukalhan.swap.userdata.di

import cz.cvut.fukalhan.swap.userdata.data.FirebaseReviewRepository
import cz.cvut.fukalhan.swap.userdata.data.FirebaseUserRepository
import cz.cvut.fukalhan.swap.userdata.domain.AddReviewUseCase
import cz.cvut.fukalhan.swap.userdata.domain.ChangeProfilePictureUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetNotificationUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserDataUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserListUseCase
import cz.cvut.fukalhan.swap.userdata.domain.GetUserReviewsUseCase
import cz.cvut.fukalhan.swap.userdata.domain.UpdateBioUseCase
import cz.cvut.fukalhan.swap.userdata.domain.repo.ReviewRepository
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userDataModule = module {
    singleOf(::FirebaseUserRepository) bind UserRepository::class
    singleOf(::FirebaseReviewRepository) bind ReviewRepository::class
    factoryOf(::GetUserDataUseCase)
    factoryOf(::AddReviewUseCase)
    factoryOf(::GetUserReviewsUseCase)
    factoryOf(::ChangeProfilePictureUseCase)
    factoryOf(::UpdateBioUseCase)
    factoryOf(::GetNotificationUseCase)
    factoryOf(::GetUserListUseCase)
}
