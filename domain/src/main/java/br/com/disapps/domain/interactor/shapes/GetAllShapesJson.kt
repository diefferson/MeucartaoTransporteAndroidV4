package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase
import br.com.disapps.domain.listeners.DownloadProgressListener
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository
import io.reactivex.Single

class GetAllShapesJson(val shapesRepository: ShapesRepository, val threadExecutor: ThreadExecutor,
                          val postExecutionThread: PostExecutionThread): SingleUseCase<String, GetAllShapesJson.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<String> {
        return shapesRepository.jsonShapes(params.city, params.downloadProgressListener)
    }

    class Params(val city : City, val downloadProgressListener: DownloadProgressListener)
}