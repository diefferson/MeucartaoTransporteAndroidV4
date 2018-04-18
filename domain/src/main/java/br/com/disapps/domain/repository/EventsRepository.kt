package br.com.disapps.domain.repository

import br.com.disapps.domain.model.*
import io.reactivex.Completable
import io.reactivex.Observable

interface EventsRepository{
    fun postEvent(event: Event) : Completable
    fun getUpdateDataEvent() : Observable<UpdateDataEvent>
    fun getUpdateLinesEvent() : Observable<UpdateLinesEvent>
    fun getUpdateSchedulesEvent() : Observable<UpdateSchedulesEvent>
}