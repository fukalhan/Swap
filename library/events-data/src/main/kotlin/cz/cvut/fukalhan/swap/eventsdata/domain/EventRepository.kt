package cz.cvut.fukalhan.swap.eventsdata.domain

import cz.cvut.fukalhan.swap.eventsdata.data.Response
import cz.cvut.fukalhan.swap.eventsdata.data.ResponseFlag

interface EventRepository {

    suspend fun addEvent(): Response<ResponseFlag>
}
