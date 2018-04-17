package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.ObservableUseCase
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.domain.model.UpdateLinesEvent
import br.com.disapps.domain.repository.EventsRepository
import io.reactivex.Observable

class GetUpdateLinesEvent(private val eventsRepository: EventsRepository, val threadExecutor: ThreadExecutor,
                          val postExecutionThread: PostExecutionThread): ObservableUseCase<UpdateLinesEvent, Unit>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Observable<UpdateLinesEvent> {
        return eventsRepository.getUpdateLinesEvent()
    }
}