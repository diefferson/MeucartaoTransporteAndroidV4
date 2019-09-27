package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Shape
import br.com.disapps.domain.repository.ShapesRepository

class GetShapes(private val shapesRepository: ShapesRepository): UseCase<List<Shape>, GetShapes.Params>() {

    override suspend fun run(params: Params): List<Shape> {
        return shapesRepository.getShapes(params.codeLine)
    }

    class Params(val codeLine : String)
}