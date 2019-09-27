package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.repository.LinesRepository

class SaveAllLinesJsonOnly(val linesRepository: LinesRepository): UseCase<Unit, SaveAllLinesJsonOnly.Params>(){

    override suspend fun run(params: Params) {
        return linesRepository.saveAllLinesFromJson(params.filePath)
    }

    class Params (val filePath:String)

}