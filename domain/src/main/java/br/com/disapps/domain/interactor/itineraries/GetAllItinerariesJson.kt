package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository

class GetAllItinerariesJson(val itinerariesRepository: ItinerariesRepository, val contextExecutor: ContextExecutor,
                            val postExecutionContext: PostExecutionContext): BaseUseCase<String, GetAllItinerariesJson.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params): String {
        return itinerariesRepository.jsonItineraries(params.city, params.downloadProgressListener)
    }

    class Params(val city : City, val downloadProgressListener: DownloadProgressListener)
}