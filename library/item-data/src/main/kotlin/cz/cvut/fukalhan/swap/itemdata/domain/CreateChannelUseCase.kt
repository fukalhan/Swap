package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.CreateChannelResponseFlag
import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ChannelRepository
import cz.cvut.fukalhan.swap.itemdata.model.Channel

class CreateChannelUseCase(private val channelRepository: ChannelRepository) {
    suspend fun createChannel(channel: Channel): DataResponse<CreateChannelResponseFlag, String> {
        val channelExistResponse = channelRepository.channelExist(channel)
        return if (channelExistResponse.success) {
            channelExistResponse
        } else {
            channelRepository.createChannel(channel)
        }
    }
}
