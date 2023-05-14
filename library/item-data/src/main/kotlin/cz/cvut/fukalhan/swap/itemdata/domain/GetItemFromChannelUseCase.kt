package cz.cvut.fukalhan.swap.itemdata.domain

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.domain.repo.ChannelRepository
import cz.cvut.fukalhan.swap.itemdata.model.Item

class GetItemFromChannelUseCase(private val channelRepository: ChannelRepository) {
    suspend fun getItem(channelId: String): DataResponse<Item> {
        return channelRepository.getItemFromChannel(channelId)
    }
}
