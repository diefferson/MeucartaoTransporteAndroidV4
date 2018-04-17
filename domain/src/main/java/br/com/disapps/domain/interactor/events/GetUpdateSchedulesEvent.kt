package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.ObservableUseCase
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.domain.model.UpdateSchedulesEvent
import br.com.disapps.domain.repository.EventsRepository
import io.reactivex.Observable

class GetUpdateSchedulesEvent(private val eventsRepository: EventsRepository, val threadExecutor: ThreadExecutor,
                              val postExecutionThread: PostExecutionThread): ObservableUseCase<UpdateSchedulesEvent, Unit>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Observable<UpdateSchedulesEvent> {
        return eventsRepository.getUpdateSchedulesEvent()
    }
}