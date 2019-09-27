package br.com.disapps.domain.interactor.shapes

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.City
import br.com.disapps.domain.repository.ShapesRepository

class SaveAllShapesJson(val shapesRepository: ShapesRepository) : UseCase<Unit, SaveAllShapesJson.Params>(){

    override suspend fun run(params: Params){
        return shapesRepository.saveAllFromJson(params.city, params.filePath)
    }

    class Params(val city : City,  val filePath:String)

}