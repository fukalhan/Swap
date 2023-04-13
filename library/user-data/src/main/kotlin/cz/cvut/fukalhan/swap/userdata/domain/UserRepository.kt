package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.model.UserProfile

interface UserRepository {
    suspend fun getUserProfileData(uid: String): Response<ResponseFlag, UserProfile>
}
