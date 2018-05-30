package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.UpdateSchedulesEventComplete
import br.com.disapps.domain.repository.EventsRepository

class GetUpdateSchedulesEvent(private val eventsRepository: EventsRepository,
                              val contextExecutor: ContextExecutor, val postExecutionContext: PostExecutionContext,
                              val logException: LogException): UseCase<UpdateSchedulesEventComplete, Unit>(contextExecutor,postExecutionContext,logException){

    override suspend fun buildUseCaseObservable(params: Unit): UpdateSchedulesEventComplete {
        return eventsRepository.getUpdateSchedulesEvent().receive()
    }
}