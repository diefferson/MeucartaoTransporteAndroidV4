package br.com.disapps.data.repository

import br.com.disapps.data.events.EventBus
import br.com.disapps.domain.model.*
import br.com.disapps.domain.repository.EventsRepository
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class EventsDataRepository(private val eventBus: EventBus) : EventsRepository{

    override suspend fun postEvent(event: Event) {
        return eventBus.send(event)
    }

    override suspend fun getUpdateLinesEvent(): ReceiveChannel<UpdateLinesEventComplete> {
        return eventBus.asChannel(UpdateLinesEventComplete::class.java)
    }

    override suspend fun getUpdateSchedulesEvent(): ReceiveChannel<UpdateSchedulesEventComplete> {
        return eventBus.asChannel(UpdateSchedulesEventComplete::class.java)
    }
}