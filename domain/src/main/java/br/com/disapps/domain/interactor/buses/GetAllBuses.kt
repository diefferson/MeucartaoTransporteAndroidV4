package br.com.disapps.domain.interactor.buses

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.ObservableUseCase
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.repository.BusesRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class GetAllBuses(private val busesRepository: BusesRepository,
                  val threadExecutor: ThreadExecutor, val postExecutionThread: PostExecutionThread) : ObservableUseCase<List<Bus>, GetAllBuses.Params>(threadExecutor,postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Observable<List<Bus>> {
        return busesRepository
                .getAllBuses(params.codeLine)
                .repeatWhen { it.delay(30, TimeUnit.SECONDS)}
    }

    class Params(val codeLine : String)
}