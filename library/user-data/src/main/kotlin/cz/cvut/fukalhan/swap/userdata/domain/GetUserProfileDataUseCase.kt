package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.User

class GetUserProfileDataUseCase(private val repository: UserRepository) {
    suspend fun getUserProfileData(uid: String): DataResponse<ResponseFlag, User> {
        return repository.getUserProfileData(uid)
    }
}
