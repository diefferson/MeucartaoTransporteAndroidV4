package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.BaseUseCase
import br.com.disapps.domain.model.Shape
import br.com.disapps.domain.repository.ShapesRepository

class GetShapes(private val shapesRepository: ShapesRepository, val contextExecutor: ContextExecutor,
                val postExecutionContext: PostExecutionContext): BaseUseCase<List<Shape>, GetShapes.Params>(contextExecutor, postExecutionContext) {

    override suspend fun buildUseCaseObservable(params: Params): List<Shape> {
        return shapesRepository.getShapes(params.codeLine)
    }

    class Params(val codeLine : String)
}