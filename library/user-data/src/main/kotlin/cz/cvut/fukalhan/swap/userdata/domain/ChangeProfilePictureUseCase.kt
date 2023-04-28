package cz.cvut.fukalhan.swap.userdata.domain

import android.net.Uri
import cz.cvut.fukalhan.swap.userdata.data.Response
import cz.cvut.fukalhan.swap.userdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository

class ChangeProfilePictureUseCase(private val userRepository: UserRepository) {
    suspend fun changeProfilePic(userId: String, uri: Uri): Response<ResponseFlag> {
        return userRepository.changeUserProfilePicture(userId, uri)
    }
}
