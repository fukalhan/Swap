package cz.cvut.fukalhan.swap.userdata.domain

import cz.cvut.fukalhan.swap.userdata.data.DataResponse
import cz.cvut.fukalhan.swap.userdata.domain.repo.UserRepository
import cz.cvut.fukalhan.swap.userdata.model.Notification

class GetNotificationUseCase(private val userRepository: UserRepository) {

    suspend fun getNotification(userId: String, itemId: String): DataResponse<Notification> {
        return userRepository.getNotificationData(userId, itemId)
    }
}
