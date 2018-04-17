package br.com.disapps.domain.interactor.events

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.ObservableUseCase
import br.com.disapps.domain.model.UpdateCwbItinerariesEvent
import br.com.disapps.domain.model.UpdateDataEvent
import br.com.disapps.domain.repository.EventsRepository
import io.reactivex.Observable

class GetUpdateCwbItinerariesEvent(private val eventsRepository: EventsRepository, val threadExecutor: ThreadExecutor,
                                   val postExecutionThread: PostExecutionThread): ObservableUseCase<UpdateCwbItinerariesEvent, Unit>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Unit): Observable<UpdateCwbItinerariesEvent> {
        return eventsRepository.getUpdateCwbItinerariesEvent()
    }
}