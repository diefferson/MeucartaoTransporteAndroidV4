package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.executor.PostExecutionThread
import br.com.disapps.domain.executor.ThreadExecutor
import br.com.disapps.domain.interactor.base.SingleUseCase

import br.com.disapps.domain.model.Shape
import br.com.disapps.domain.repository.ShapesRepository
import io.reactivex.Single

class GetShapes(private val shapesRepository: ShapesRepository, val threadExecutor: ThreadExecutor,
                val postExecutionThread: PostExecutionThread): SingleUseCase<List<Shape>, GetShapes.Params>(threadExecutor, postExecutionThread){

    override fun buildUseCaseObservable(params: Params): Single<List<Shape>> {
        return shapesRepository.getShapes(params.codeLine)
    }

    class Params(val codeLine : String)
}