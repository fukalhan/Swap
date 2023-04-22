package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ChannelRepository
import cz.cvut.fukalhan.swap.itemdata.model.Channel

class CreateChannelUseCase(private val channelRepository: ChannelRepository) {
    suspend fun createChannel(channel: Channel): DataResponse<ResponseFlag, String> {
        val channelExistResponse = channelRepository.channelExist(channel)
        return if (channelExistResponse.success && channelExistResponse.data != null) {
            channelExistResponse
        } else {
            channelRepository.createChannel(channel)
        }
    }
}
