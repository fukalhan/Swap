package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ChannelRepository
import cz.cvut.fukalhan.swap.itemdata.model.Channel

class CreateChannelUseCase(private val channelRepository: ChannelRepository) {
    suspend fun createChannel(channel: Channel): DataResponse<String> {
        val channelExistResponse = channelRepository.channelExist(channel)
        return if (channelExistResponse is DataResponse.Success) {
            channelExistResponse
        } else {
            channelRepository.createChannel(channel)
        }
    }
}
