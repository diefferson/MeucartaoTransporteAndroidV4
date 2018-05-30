package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.Event
import br.com.disapps.domain.repository.EventsRepository

class PostEvent(private val eventsRepository: EventsRepository, val contextExecutor: ContextExecutor,
                val postExecutionContext: PostExecutionContext,
                val logException: LogException): UseCaseCompletable<PostEvent.Params>(contextExecutor, postExecutionContext, logException){

    override suspend fun buildUseCaseObservable(params: Params) {
        return eventsRepository.postEvent(params.event)
    }

    class Params (val event: Event)

}