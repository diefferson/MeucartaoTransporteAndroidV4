package br.com.disapps.data.repository

import br.com.disapps.data.events.RxBus
import br.com.disapps.domain.model.*
import br.com.disapps.domain.repository.EventsRepository
import io.reactivex.Completable
import io.reactivex.Observable

class EventsDataRepository(private val rxBus: RxBus) : EventsRepository{

    override fun postEvent(event: Event): Completable {
        return Completable.defer {
            rxBus.send(event)
            Completable.complete()
        }
    }

    override fun getUpdateDataEvent(): Observable<UpdateDataEvent> {
        return rxBus.toObservable(UpdateDataEvent::class.java)
    }

    override fun getUpdateLinesEvent(): Observable<UpdateLinesEvent> {
        return rxBus.toObservable(UpdateLinesEvent::class.java)
    }

    override fun getUpdateSchedulesEvent(): Observable<UpdateSchedulesEvent> {
        return rxBus.toObservable(UpdateSchedulesEvent::class.java)
    }

    override fun getUpdateCwbItinerariesEvent(): Observable<UpdateCwbItinerariesEvent> {
        return rxBus.toObservable(UpdateCwbItinerariesEvent::class.java)
    }

    override fun getUpdateMetItinerariesEvent(): Observable<UpdateMetItinerariesEvent> {
        return rxBus.toObservable(UpdateMetItinerariesEvent::class.java)
    }

    override fun getUpdateCwbShapesEvent(): Observable<UpdateCwbShapesEvent> {
        return rxBus.toObservable(UpdateCwbShapesEvent::class.java)
    }

    override fun getUpdateMetShapesEvent(): Observable<UpdateMetShapesEvent> {
        return rxBus.toObservable(UpdateMetShapesEvent::class.java)
    }
}