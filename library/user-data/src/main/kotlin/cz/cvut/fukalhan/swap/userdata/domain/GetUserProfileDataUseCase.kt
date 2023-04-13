package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.model.UserProfile

class GetUserProfileDataUseCase(private val repository: UserRepository) {
    suspend fun getUserProfileData(uid: String): Response<ResponseFlag, UserProfile> {
        return repository.getUserProfileData(uid)
    }
}
