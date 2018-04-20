package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.BusStop
import br.com.disapps.domain.repository.ItinerariesRepository

class GetItinerary(private val itinerariesRepository: ItinerariesRepository,val contextExecutor: ContextExecutor,
                   val postExecutionContext: PostExecutionContext): BaseUseCase<List<BusStop>, GetItinerary.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params): List<BusStop> {
        return itinerariesRepository.getItinerary(params.codeLine, params.direction)
    }

    class Params(val codeLine : String, val direction: String)
}