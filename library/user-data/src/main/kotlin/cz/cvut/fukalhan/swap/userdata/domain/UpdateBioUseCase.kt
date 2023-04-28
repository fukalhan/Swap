package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository

class UpdateBioUseCase(private val userRepository: UserRepository) {

    suspend fun updateUserBio(userId: String, bio: String): Response<ResponseFlag> {
        return userRepository.updateUserBio(userId, bio)
    }
}
