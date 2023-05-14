package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.User

class GetUserListUseCase(private val userRepository: UserRepository) {

    suspend fun getUsersById(usersIds: List<String>): DataResponse<List<User>> {
        return userRepository.getUsersById(usersIds)
    }
}
