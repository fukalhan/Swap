package cz.cvut.fukalhan.swap.itemdata.domain.repo

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.model.Channel
import cz.cvut.fukalhan.swap.itemdata.model.Item

interface ChannelRepository {
    suspend fun createChannel(channel: Channel): DataResponse<String>
    suspend fun channelExist(channel: Channel): DataResponse<String>
    suspend fun getItemFromChannel(channelId: String): DataResponse<Item>
}
