package br.com.disapps.data.events

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class RxBus {

    private val bus = PublishSubject.create<Any>()

    fun send(o: Any) {
        bus.onNext(o)
    }

    fun error(){
        bus.onError(Throwable())
    }

    fun <T> toObservable(klazz: Class<T>): Observable<T> {
        return bus.ofType(klazz)
    }
}


