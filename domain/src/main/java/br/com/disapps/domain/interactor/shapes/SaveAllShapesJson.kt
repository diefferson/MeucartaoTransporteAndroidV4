package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.executor.ContextExecutor
import br.com.disapps.domain.executor.PostExecutionContext
import br.com.disapps.domain.interactor.base.UseCaseCompletable
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository

class SaveAllShapesJson(val shapesRepository: ShapesRepository, val contextExecutor: ContextExecutor,
                        val postExecutionContext: PostExecutionContext) : UseCaseCompletable<SaveAllShapesJson.Params>(contextExecutor, postExecutionContext){

    override suspend fun buildUseCaseObservable(params: Params){
        return shapesRepository.saveAllFromJson(params.json, params.city)
    }

    class Params (val json: String, val city : City)
}