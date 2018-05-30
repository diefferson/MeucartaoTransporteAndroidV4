package br.com.disapps.domain.repository

import br.com.disapps.domain.model.Event
import br.com.disapps.domain.model.UpdateLinesEventComplete
import br.com.disapps.domain.model.UpdateSchedulesEventComplete
import kotlinx.coroutines.experimental.channels.ReceiveChannel

interface EventsRepository{
    suspend fun postEvent(event: Event)
    suspend fun getUpdateLinesEvent() : ReceiveChannel<UpdateLinesEventComplete>
    suspend fun getUpdateSchedulesEvent() : ReceiveChannel<UpdateSchedulesEventComplete>
}