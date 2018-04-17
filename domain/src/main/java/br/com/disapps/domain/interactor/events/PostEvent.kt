package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.CompletableUseCase
import br.com.disapps.domain.model.Event
import br.com.disapps.domain.repository.EventsRepository
import io.reactivex.Completable

class PostEvent(private val eventsRepository: EventsRepository, val threadExecutor: ThreadExecutor,
                val postExecutionThread: PostExecutionThread): CompletableUseCase<PostEvent.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Completable {
        return eventsRepository.postEvent(params.event)
    }

    class Params (val event: Event)

}