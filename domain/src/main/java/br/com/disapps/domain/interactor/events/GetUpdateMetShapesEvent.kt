package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.ObservableUseCase
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.domain.model.UpdateMetShapesEvent
import br.com.disapps.domain.repository.EventsRepository
import io.reactivex.Observable

class GetUpdateMetShapesEvent(private val eventsRepository: EventsRepository, val threadExecutor: ThreadExecutor,
                              val postExecutionThread: PostExecutionThread): ObservableUseCase<UpdateMetShapesEvent, Unit>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Observable<UpdateMetShapesEvent> {
        return eventsRepository.getUpdateMetShapesEvent()
    }
}