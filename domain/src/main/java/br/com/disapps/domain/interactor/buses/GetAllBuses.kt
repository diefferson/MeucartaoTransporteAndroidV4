package br.com.disapps.domain.interactor.buses

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.Bus
import br.com.disapps.domain.repository.BusesRepository

class GetAllBuses(private val busesRepository: BusesRepository,
                  val contextExecutor: ContextExecutor, val postExecutionContext: PostExecutionContext) : BaseUseCase<List<Bus>, GetAllBuses.Params>(contextExecutor,postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params): List<Bus> {
        return busesRepository.getAllBuses(params.codeLine)
    }

    class Params(val codeLine : String)

}