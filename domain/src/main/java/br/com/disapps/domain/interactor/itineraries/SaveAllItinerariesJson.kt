package br.com.disapps.domain.interactor.itineraries

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ItinerariesRepository

class SaveAllItinerariesJson(val itinerariesRepository: ItinerariesRepository, val contextExecutor: ContextExecutor,
                             val postExecutionContext: PostExecutionContext) : UseCaseCompletable<SaveAllItinerariesJson.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params) {
        return itinerariesRepository.saveAllFromJson(params.filePath, params.city, params.downloadProgressListener)
    }

    class Params (val filePath:String,val city : City,  val downloadProgressListener: DownloadProgressListener)
}