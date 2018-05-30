package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository

class GetAllItineraries(private val itinerariesRepository: ItinerariesRepository, val contextExecutor: ContextExecutor,
                        val postExecutionContext: PostExecutionContext,
                        val logException: LogException): UseCase<List<BusStop>, GetAllItineraries.Params>(contextExecutor, postExecutionContext, logException){

    override suspend fun buildUseCaseObservable(params: Params): List<BusStop> {
        return itinerariesRepository.getAllItineraries(params.codeLine)
    }

    class Params(val codeLine : String)
}