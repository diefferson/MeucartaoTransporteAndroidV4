package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.repository.ItinerariesRepository

class GetItineraryDirections(private val itinerariesRepository: ItinerariesRepository, val contextExecutor: ContextExecutor,
                             val postExecutionContext: PostExecutionContext): BaseUseCase<List<String>, GetItineraryDirections.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params): List<String> {
        return itinerariesRepository.getItineraryDirections(params.codeLine)
    }

    class Params(val codeLine : String)
}