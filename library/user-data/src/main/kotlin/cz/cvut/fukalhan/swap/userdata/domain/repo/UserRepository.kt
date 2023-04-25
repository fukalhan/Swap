package cz.cvut.fukalhan.swap.userdata.domain.repo

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.model.User

interface UserRepository {
    suspend fun getUserProfileData(uid: String): DataResponse<ResponseFlag, User>

    suspend fun getUserRating(userId: String): DataResponse<ResponseFlag, Float>
}
