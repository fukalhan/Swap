package cz.cvut.fukalhan.swap.itemdata.domain.repo

import cz.cvut.fukalhan.swap.itemdata.data.DataResponse
import cz.cvut.fukalhan.swap.itemdata.data.ResponseFlag
import cz.cvut.fukalhan.swap.itemdata.model.Channel

interface ChannelRepository {
    suspend fun createChannel(channel: Channel): DataResponse<ResponseFlag, String>
}
