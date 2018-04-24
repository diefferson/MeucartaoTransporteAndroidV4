package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.UpdateLinesEventComplete
import br.com.disapps.domain.model.UpdateSchedulesEventComplete
import br.com.disapps.domain.repository.EventsRepository
import kotlinx.coroutines.experimental.channels.ReceiveChannel

class GetUpdateSchedulesEvent(private val eventsRepository: EventsRepository,
                              val contextExecutor: ContextExecutor, val postExecutionContext: PostExecutionContext): UseCase<UpdateSchedulesEventComplete, Unit>(contextExecutor,postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Unit): UpdateSchedulesEventComplete {
        return eventsRepository.getUpdateSchedulesEvent().receive()
    }
}