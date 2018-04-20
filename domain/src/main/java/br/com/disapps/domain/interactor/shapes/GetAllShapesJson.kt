package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository

class GetAllShapesJson(val shapesRepository: ShapesRepository, val contextExecutor: ContextExecutor,
                       val postExecutionContext: PostExecutionContext): BaseUseCase<String, GetAllShapesJson.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): String {
        return shapesRepository.jsonShapes(params.city, params.downloadProgressListener)
    }

    class Params(val city : City, val downloadProgressListener: DownloadProgressListener)
}