package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository
import io.reactivex.Single

class GetAllItinerariesJson(val itinerariesRepository: ItinerariesRepository, val threadExecutor: ThreadExecutor,
                            val postExecutionThread: PostExecutionThread): SingleUseCase<String, GetAllItinerariesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<String> {
        return itinerariesRepository.jsonItineraries(params.city, params.downloadProgressListener)
    }

    class Params(val city : City, val downloadProgressListener: DownloadProgressListener)
}