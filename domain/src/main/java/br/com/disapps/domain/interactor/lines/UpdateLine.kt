package br.com.disapps.domain.interactor.lines

import br.com.disapps.domain.interactor.base.UseCase
import br.com.disapps.domain.model.Line
import br.com.disapps.domain.repository.LinesRepository

class UpdateLine(val linesRepository: LinesRepository): UseCase<Unit, UpdateLine.Params>(){

    override suspend fun run(params: Params) {
        return linesRepository.updateLine(params.line)
    }

    class Params (val line: Line)

}