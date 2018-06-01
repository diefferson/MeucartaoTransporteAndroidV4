package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.exception.LogException
import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository

class SaveAllShapesJson(val shapesRepository: ShapesRepository, val contextExecutor: ContextExecutor,
                        val postExecutionContext: PostExecutionContext,
                        val logException: LogException) : UseCaseCompletable<SaveAllShapesJson.Params>(contextExecutor, postExecutionContext,logException){

    override suspend fun buildUseCaseObservable(params: Params){
        return shapesRepository.saveAllFromJson(params.city, params.filePath)
    }

    class Params(val city : City,  val filePath:String)

}